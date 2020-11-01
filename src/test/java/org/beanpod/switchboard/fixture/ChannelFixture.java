package org.beanpod.switchboard.fixture;

import org.beanpod.switchboard.entity.ChannelEntity;

import java.util.HashSet;
import java.util.Set;

public class ChannelFixture {

    public static ChannelEntity getChannel1(){
        return new ChannelEntity( 8080,"local",null,null);
    }

    public static ChannelEntity getChannel2(){
        return new ChannelEntity( 9090,"dev",null,null);
    }

    public static Set<ChannelEntity> getSetOfChannels(){
        Set<ChannelEntity> setOfChannels= new HashSet<>();
        setOfChannels.add(getChannel1());
        setOfChannels.add(getChannel1());
        return setOfChannels;
    }
}