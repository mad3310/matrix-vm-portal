package com.letv.portal.service.openstack.resource.manager.impl;

import java.io.Closeable;
import java.util.*;

import com.letv.portal.service.openstack.util.constants.Constants;
import org.jclouds.ContextBuilder;

import com.letv.portal.model.cloudvm.CloudvmRegion;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.exception.RegionNotFoundException;
import com.letv.portal.service.openstack.impl.OpenStackConf;
import com.letv.portal.service.openstack.impl.OpenStackServiceImpl;
import com.letv.portal.service.openstack.impl.OpenStackUser;
import com.letv.portal.service.openstack.resource.Region;
import com.letv.portal.service.openstack.resource.manager.ResourceManager;

public abstract class AbstractResourceManager<ApiType extends Closeable>
		implements ResourceManager, Closeable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1539965976463585808L;
	// protected OpenStackServiceGroup openStackServiceGroup;
	protected OpenStackConf openStackConf;
	protected OpenStackUser openStackUser;

	// protected ICloudvmRegionService cloudvmRegionService;
	// protected ITemplateMessageSender defaultEmailSender;

	public AbstractResourceManager() {
	}

	public AbstractResourceManager(OpenStackConf openStackConf,
			OpenStackUser openStackUser) {
		// this.openStackServiceGroup = openStackServiceGroup;
		this.openStackConf = openStackConf;
		this.openStackUser = openStackUser;
		// this.cloudvmRegionService = openStackServiceGroup
		// .getCloudvmRegionService();
		// this.defaultEmailSender =
		// openStackServiceGroup.getDefaultEmailSender();
	}

	protected abstract String getProviderOrApi();

	protected abstract Class<ApiType> getApiClass();

	public ApiType openApi() {
//		Iterable<Module> modules = ImmutableSet
//				.<Module> of(new SLF4JLoggingModule());

		ApiType api = ContextBuilder
				.newBuilder(getProviderOrApi())
				.endpoint(openStackConf.getPublicEndpoint())
				.credentials(
						openStackUser.getUserId() + ":"
								+ openStackUser.getUserId(),
						openStackUser.getPassword()).modules(Constants.jcloudsContextBuilderModules)
				.buildApi(getApiClass());
		return api;
	}

	public <ReturnType> ReturnType runWithApi(ApiRunnable<ApiType,ReturnType> task) throws OpenStackException {
		try {
			ReturnType returnObj;
			ApiType api = openApi();
			try {
				returnObj = task.run(api);
			} finally {
				api.close();
			}
			return returnObj;
		} catch (OpenStackException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new OpenStackException("后台错误", ex);
		}
	}

	public void checkRegion(String region) throws OpenStackException,RegionNotFoundException {
		if (!getRegions().contains(region)) {
			throw new RegionNotFoundException(region);
		}
	}

	@Override
	public List<Region> listRegion() throws OpenStackException {
		Set<String> regionCodes = getRegions();
		List<CloudvmRegion> cloudvmRegions = OpenStackServiceImpl
				.getOpenStackServiceGroup().getCloudvmRegionService().selectAll();
		List<Region> regions = new LinkedList<Region>();
		for (CloudvmRegion cloudvmRegion : cloudvmRegions) {
			if (regionCodes.contains(cloudvmRegion.getCode())) {
				Region region = new Region();
				region.setId(cloudvmRegion.getCode());
				region.setName(cloudvmRegion.getDisplayName());
				regions.add(region);
			}
		}
		return regions;
	}

	@Override
	public Map<String, Map<String, Map<Integer, Region>>> getGroupRegions()
			throws OpenStackException {
		Set<String> regionCodes = getRegions();
		Map<String, Map<String, Map<Integer, Region>>> groupRegions = new HashMap<String, Map<String, Map<Integer, Region>>>();
		for (String regionCode : regionCodes) {
			String[] regionCodeFragments = regionCode.split("-");
			if (regionCodeFragments.length != 3) {
				throw new OpenStackException("Region name format error.",
						"区域名称格式错误");
			}
			final String countryId = regionCodeFragments[0];
			final String cityId = regionCodeFragments[1];
			final int number = Integer.parseInt(regionCodeFragments[2]);

			CloudvmRegion cloudvmRegion = OpenStackServiceImpl
					.getOpenStackServiceGroup().getCloudvmRegionService()
					.selectByCode(regionCode);
			if (cloudvmRegion == null) {
				throw new OpenStackException("Lack of region Chinese name",
						"缺少地域的中文名");
			}
			final String regionDisplayName = cloudvmRegion.getDisplayName();
			String[] regionDisplayNameFragments = regionDisplayName.split("-");
			if (regionDisplayNameFragments.length != 3) {
				throw new OpenStackException(
						"Region Chinese name format error.", "区域中文名格式错误");
			}
			final String country = regionDisplayNameFragments[0];
			final String city = regionDisplayNameFragments[1];

			final Region region = new Region(regionCode, regionDisplayName,
					countryId, country, cityId, city, number);

			Map<String, Map<Integer, Region>> countryGroupRegions = groupRegions
					.get(countryId);
			if (countryGroupRegions == null) {
				countryGroupRegions = new HashMap<String, Map<Integer, Region>>();
				groupRegions.put(countryId, countryGroupRegions);
			}

			Map<Integer, Region> cityCountryGroupRegions = countryGroupRegions
					.get(cityId);
			if (cityCountryGroupRegions == null) {
				cityCountryGroupRegions = new HashMap<Integer, Region>();
				countryGroupRegions.put(cityId, cityCountryGroupRegions);
			}

			cityCountryGroupRegions.put(number, region);
		}
		return groupRegions;
	}

	public Set<String> getGroupRegions(String regionGroup)
			throws OpenStackException {
		String[] regionGroupFragments = regionGroup.split("-");
		if (regionGroupFragments.length > 3 || regionGroupFragments.length < 1) {
			throw new OpenStackException("Region group name format error.",
					"区域组名称格式错误");
		}

		Set<String> regions = getRegions();
		Set<String> matchedRegions = new HashSet<String>();
		for (String region : regions) {
			String[] regionFragments = region.split("-");
			if (regionFragments.length != 3) {
				throw new OpenStackException("Region name format error.",
						"区域名称格式错误");
			}

			for (int i = 0; i < regionGroupFragments.length; i++) {
				if (regionGroupFragments[i].equals(regionFragments[i])) {
					if (i == regionGroupFragments.length - 1) {
						matchedRegions.add(region);
					}
				} else {
					break;
				}
			}
		}
		return matchedRegions;
	}

	public String getRegionDisplayName(String regionCode)
			throws OpenStackException {
		CloudvmRegion region = OpenStackServiceImpl.getOpenStackServiceGroup()
				.getCloudvmRegionService().selectByCode(regionCode);
		if (region == null) {
			throw new OpenStackException("Lack of region Chinese name",
					"缺少地域的中文名");
		}
		return region.getDisplayName();
	}

	protected Map<String, String> getRegionCodeToDisplayNameMap() {
		Map<String, String> map = new HashMap<String, String>();
		for (CloudvmRegion region : OpenStackServiceImpl
				.getOpenStackServiceGroup().getCloudvmRegionService()
				.selectAll()) {
			map.put(region.getCode(), region.getDisplayName());
		}
		return map;
	}

	public OpenStackConf getOpenStackConf() {
		return openStackConf;
	}

	public OpenStackUser getOpenStackUser() {
		return openStackUser;
	}

	private static final Set<String> limitedUserEmails = new HashSet<String>();
	static {
		limitedUserEmails.add("zhangwenming@letv.com");
		limitedUserEmails.add("zhoubingzheng@letv.com");
		limitedUserEmails.add("yuelongguang@letv.com");
		limitedUserEmails.add("jiangfei@letv.com");
		limitedUserEmails.add("zhouxianguang@letv.com");
		limitedUserEmails.add("jiangfei5945@hotmail.com");
		limitedUserEmails.add("gaomin@letv.com");
		limitedUserEmails.add("yaokuo@letv.com");
		limitedUserEmails.add("lisuxiao@letv.com");
		limitedUserEmails.add("liuhao1@letv.com");
	}

	protected void checkUserEmail() throws OpenStackException {
		if (!isAuthority()) {
			throw new OpenStackException("The user is not in limited users",
					"对不起，您没有权限。");
		}
	}

	public boolean isAuthority() {
//		return limitedUserEmails.contains(openStackUser.getEmail());
		return true;
	}
}
