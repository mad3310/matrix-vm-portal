package com.letv.portal.service.openstack.resource.manager.impl;

import java.io.Closeable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.exception.RegionNotFoundException;
import com.letv.portal.service.openstack.impl.OpenStackConf;
import com.letv.portal.service.openstack.impl.OpenStackUser;
import com.letv.portal.service.openstack.resource.Region;
import com.letv.portal.service.openstack.resource.manager.ResourceManager;

public abstract class AbstractResourceManager implements ResourceManager,
		Closeable {
	protected OpenStackConf openStackConf;
	protected OpenStackUser openStackUser;

	public AbstractResourceManager(OpenStackConf openStackConf,
			OpenStackUser openStackUser) {
		this.openStackConf = openStackConf;
		this.openStackUser = openStackUser;
	}

	public void checkRegion(String region) throws RegionNotFoundException {
		if (!getRegions().contains(region)) {
			throw new RegionNotFoundException(region);
		}
	}

	@Override
	public Map<String, Map<String, Map<Integer, Region>>> getGroupRegions()
			throws OpenStackException {
		Set<String> regionStrs = getRegions();
		Map<String, Map<String, Map<Integer, Region>>> groupRegions = new HashMap<String, Map<String, Map<Integer, Region>>>();
		for (String regionStr : regionStrs) {
			String[] regionStrFragments = regionStr.split("-");

			if (regionStrFragments.length != 3) {
				throw new OpenStackException("Region name format error.",
						"区域名称格式错误");
			}

			final String country = regionStrFragments[0];
			final String city = regionStrFragments[1];
			final int number = Integer.parseInt(regionStrFragments[2]);

			final Region region = new Region(regionStr, regionStr, country,
					country, city, city, number);

			Map<String, Map<Integer, Region>> countryGroupRegions = groupRegions
					.get(country);
			if (countryGroupRegions == null) {
				countryGroupRegions = new HashMap<String, Map<Integer, Region>>();
				groupRegions.put(country, countryGroupRegions);
			}

			Map<Integer, Region> cityCountryGroupRegions = countryGroupRegions
					.get(city);
			if (cityCountryGroupRegions == null) {
				cityCountryGroupRegions = new HashMap<Integer, Region>();
				countryGroupRegions.put(city, cityCountryGroupRegions);
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
}
