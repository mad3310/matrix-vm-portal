package com.letv.portal.service.openstack.resource.manager.impl;

import java.io.Closeable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.letv.common.email.ITemplateMessageSender;
import com.letv.portal.model.cloudvm.CloudvmRegion;
import com.letv.portal.service.cloudvm.ICloudvmRegionService;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.exception.RegionNotFoundException;
import com.letv.portal.service.openstack.impl.OpenStackConf;
import com.letv.portal.service.openstack.impl.OpenStackServiceGroup;
import com.letv.portal.service.openstack.impl.OpenStackUser;
import com.letv.portal.service.openstack.resource.Region;
import com.letv.portal.service.openstack.resource.manager.ResourceManager;

public abstract class AbstractResourceManager implements ResourceManager,
		Closeable {
	protected OpenStackServiceGroup openStackServiceGroup;
	protected OpenStackConf openStackConf;
	protected OpenStackUser openStackUser;

	protected ICloudvmRegionService cloudvmRegionService;
	protected ITemplateMessageSender defaultEmailSender;

	public AbstractResourceManager(OpenStackServiceGroup openStackServiceGroup,
			OpenStackConf openStackConf, OpenStackUser openStackUser) {
		this.openStackServiceGroup = openStackServiceGroup;
		this.openStackConf = openStackConf;
		this.openStackUser = openStackUser;
		this.cloudvmRegionService = openStackServiceGroup
				.getCloudvmRegionService();
		this.defaultEmailSender = openStackServiceGroup.getDefaultEmailSender();
	}

	public void checkRegion(String region) throws RegionNotFoundException {
		if (!getRegions().contains(region)) {
			throw new RegionNotFoundException(region);
		}
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

			CloudvmRegion cloudvmRegion = cloudvmRegionService
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
		CloudvmRegion region = this.cloudvmRegionService
				.selectByCode(regionCode);
		if (region == null) {
			throw new OpenStackException("Lack of region Chinese name",
					"缺少地域的中文名");
		}
		return region.getDisplayName();
	}

	protected Map<String, String> getRegionCodeToDisplayNameMap() {
		Map<String, String> map = new HashMap<String, String>();
		for (CloudvmRegion region : this.cloudvmRegionService.selectAll()) {
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

}
