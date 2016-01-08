package com.letv.lcp.openstack.service.task.check;

import org.jclouds.openstack.cinder.v1.CinderApi;
import org.jclouds.openstack.cinder.v1.domain.VolumeType;
import org.jclouds.openstack.glance.v1_0.GlanceApi;
import org.jclouds.openstack.neutron.v2.NeutronApi;
import org.jclouds.openstack.neutron.v2.domain.Network;
import org.jclouds.openstack.neutron.v2.domain.Subnet;
import org.jclouds.openstack.nova.v2_0.NovaApi;
import org.jclouds.openstack.nova.v2_0.domain.Flavor;
import org.jclouds.openstack.nova.v2_0.domain.Image;
import org.jclouds.openstack.nova.v2_0.domain.KeyPair;

import com.letv.lcp.cloudvm.model.task.VMCreateConf2;
import com.letv.lcp.openstack.service.manage.impl.NetworkManagerImpl;
import com.letv.lcp.openstack.service.manage.impl.VMManagerImpl;
import com.letv.lcp.openstack.service.manage.impl.VolumeManagerImpl;

/**
 * Created by zhouxianguang on 2015/10/20.
 */
public class MultiVmCreateCheckContext {
    private VMCreateConf2 vmCreateConf;
    private VMManagerImpl vmManager;
    private NetworkManagerImpl networkManager;
    private VolumeManagerImpl volumeManager;

    private NovaApi novaApi;
    private NeutronApi neutronApi;
    private CinderApi cinderApi;
    private GlanceApi glanceApi;

    private String regionDisplayName;
    private Flavor flavor;
    private Image image;
    private Network privateNetwork;
    private Subnet privateSubnet;
    private Network sharedNetwork;
    private Image snapshot;
    private VolumeType volumeType;
    private KeyPair keyPair;
    private Network floatingNetwork;
    private Long userId;

    public NovaApi getNovaApi() {
        return novaApi;
    }

    public void setNovaApi(NovaApi novaApi) {
        this.novaApi = novaApi;
    }

    public NeutronApi getNeutronApi() {
        return neutronApi;
    }

    public void setNeutronApi(NeutronApi neutronApi) {
        this.neutronApi = neutronApi;
    }

    public CinderApi getCinderApi() {
        return cinderApi;
    }

    public void setCinderApi(CinderApi cinderApi) {
        this.cinderApi = cinderApi;
    }

    public GlanceApi getGlanceApi() {
        return glanceApi;
    }

    public void setGlanceApi(GlanceApi glanceApi) {
        this.glanceApi = glanceApi;
    }

    public VMCreateConf2 getVmCreateConf() {
        return vmCreateConf;
    }

    public void setVmCreateConf(VMCreateConf2 vmCreateConf) {
        this.vmCreateConf = vmCreateConf;
    }

    public VMManagerImpl getVmManager() {
        return vmManager;
    }

    public void setVmManager(VMManagerImpl vmManager) {
        this.vmManager = vmManager;
    }

    public NetworkManagerImpl getNetworkManager() {
        return networkManager;
    }

    public void setNetworkManager(NetworkManagerImpl networkManager) {
        this.networkManager = networkManager;
    }

    public VolumeManagerImpl getVolumeManager() {
        return volumeManager;
    }

    public void setVolumeManager(VolumeManagerImpl volumeManager) {
        this.volumeManager = volumeManager;
    }

    public String getRegionDisplayName() {
        return regionDisplayName;
    }

    public void setRegionDisplayName(String regionDisplayName) {
        this.regionDisplayName = regionDisplayName;
    }

    public Flavor getFlavor() {
        return flavor;
    }

    public void setFlavor(Flavor flavor) {
        this.flavor = flavor;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Network getPrivateNetwork() {
        return privateNetwork;
    }

    public void setPrivateNetwork(Network privateNetwork) {
        this.privateNetwork = privateNetwork;
    }

    public Subnet getPrivateSubnet() {
        return privateSubnet;
    }

    public void setPrivateSubnet(Subnet privateSubnet) {
        this.privateSubnet = privateSubnet;
    }

    public Network getSharedNetwork() {
        return sharedNetwork;
    }

    public void setSharedNetwork(Network sharedNetwork) {
        this.sharedNetwork = sharedNetwork;
    }

    public Image getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(Image snapshot) {
        this.snapshot = snapshot;
    }

    public VolumeType getVolumeType() {
        return volumeType;
    }

    public void setVolumeType(VolumeType volumeType) {
        this.volumeType = volumeType;
    }

    public KeyPair getKeyPair() {
        return keyPair;
    }

    public void setKeyPair(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    public Network getFloatingNetwork() {
        return floatingNetwork;
    }

    public void setFloatingNetwork(Network floatingNetwork) {
        this.floatingNetwork = floatingNetwork;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
