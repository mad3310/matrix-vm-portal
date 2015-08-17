package com.letv.portal.service.openstack.resource.manager.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jclouds.openstack.glance.v1_0.GlanceApi;
import org.jclouds.openstack.glance.v1_0.domain.ImageDetails;
import org.jclouds.openstack.glance.v1_0.features.ImageApi;

import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.exception.RegionNotFoundException;
import com.letv.portal.service.openstack.exception.ResourceNotFoundException;
import com.letv.portal.service.openstack.impl.OpenStackConf;
import com.letv.portal.service.openstack.impl.OpenStackUser;
import com.letv.portal.service.openstack.resource.ImageResource;
import com.letv.portal.service.openstack.resource.impl.ImageResourceImpl;
import com.letv.portal.service.openstack.resource.manager.ImageManager;

public class ImageManagerImpl extends AbstractResourceManager<GlanceApi> implements
		ImageManager {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1053933611677877861L;
//	private GlanceApi glanceApi;
	
	public ImageManagerImpl() {
	}

	public ImageManagerImpl( OpenStackConf openStackConf,
			OpenStackUser openStackUser) {
		super( openStackConf, openStackUser);

//		Iterable<Module> modules = ImmutableSet
//				.<Module> of(new SLF4JLoggingModule());
//
//		glanceApi = ContextBuilder
//				.newBuilder("openstack-glance")
//				.endpoint(openStackConf.getPublicEndpoint())
//				.credentials(
//						openStackUser.getUserId() + ":"
//								+ openStackUser.getUserId(),
//						openStackUser.getPassword()).modules(modules)
//				.buildApi(GlanceApi.class);
	}

	@Override
	public void close() throws IOException {
//		glanceApi.close();
	}

	@Override
	public Set<String> getRegions() throws OpenStackException {
		return runWithApi(new ApiRunnable<GlanceApi,Set<String>>() {
			
			@Override
			public Set<String> run(GlanceApi api) throws Exception {
				return api.getConfiguredRegions();
			}
		});
	}

	@Override
	public List<ImageResource> list(final String region)
			throws OpenStackException {
		return runWithApi(new ApiRunnable<GlanceApi, List<ImageResource>>() {

			@Override
			public List<ImageResource> run(GlanceApi glanceApi) throws Exception {
				checkRegion(region);

				ImageApi imageApi = glanceApi.getImageApi(region);
				List<ImageDetails> images = imageApi.listInDetail().concat().toList();
				List<ImageResource> imageResources = new ArrayList<ImageResource>(
						images.size());
				for (ImageDetails image : images) {
					imageResources.add(new ImageResourceImpl(region, image));
				}
				return imageResources;
			}
			
		});
	}

	@Override
	public ImageResource get(final String region,final String id)
			throws OpenStackException {
		return runWithApi(new ApiRunnable<GlanceApi, ImageResource>() {

			@Override
			public ImageResource run(GlanceApi glanceApi) throws Exception {
				checkRegion(region);

				ImageApi imageApi = glanceApi.getImageApi(region);
				ImageDetails imageDetails = imageApi.get(id);
				if (imageDetails != null) {
					return new ImageResourceImpl(region, imageDetails);
				} else {
					throw new ResourceNotFoundException("Image", "镜像", id);
				}
			}
		});
	}

	@Override
	public Map<String, Map<String, ImageResource>> group(final String region)
			throws RegionNotFoundException, OpenStackException {
		return runWithApi(new ApiRunnable<GlanceApi, Map<String, Map<String, ImageResource>>>() {

			@Override
			public Map<String, Map<String, ImageResource>> run(
					GlanceApi glanceApi) throws Exception {
				checkRegion(region);

				ImageApi imageApi = glanceApi.getImageApi(region);
				List<ImageDetails> images = imageApi.listInDetail().concat()
						.toList();

				Map<String, Map<String, ImageResource>> imageResources = new HashMap<String, Map<String, ImageResource>>();
				for (ImageDetails image : images) {
					ImageResource imageResource = new ImageResourceImpl(region,
							image);

					String[] imageNameFragments = imageResource.getName()
							.split("-");
					if (imageNameFragments.length != 3) {
						throw new OpenStackException(
								"Image name format error.", "镜像名称格式错误");
					}
					String osName = imageNameFragments[0];
					String osVersion = imageNameFragments[1];
					String osArch = imageNameFragments[2];
					String osVersionAndArch = osVersion + " " + osArch;

					Map<String, ImageResource> nameImageResources = imageResources
							.get(osName);
					if (nameImageResources == null) {
						nameImageResources = new HashMap<String, ImageResource>();
						imageResources.put(osName, nameImageResources);
					}

					nameImageResources.put(osVersionAndArch, imageResource);
				}
				return imageResources;
			}
		});
	}

	@Override
	protected String getProviderOrApi() {
		return "openstack-glance";
	}

	@Override
	protected Class<GlanceApi> getApiClass() {
		return GlanceApi.class;
	}

}
