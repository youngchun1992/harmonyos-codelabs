/*
 * Copyright (c) 2021 Huawei Device Co., Ltd.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.huawei.codelab.devices;

import com.huawei.codelab.ResourceTable;

import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.LayoutScatter;
import ohos.agp.components.RecycleItemProvider;
import ohos.agp.components.Text;
import ohos.app.Context;
import ohos.distributedschedule.interwork.DeviceInfo;

import java.util.List;
import java.util.Optional;

/**
 * DevicesListAdapter
 *
 * @since 2021-01-11
 */
public class DevicesListAdapter extends RecycleItemProvider {
    private List<DeviceInfo> deviceInfoList;
    private Context context;

    public DevicesListAdapter(List<DeviceInfo> listBasicInfo, Context context) {
        this.deviceInfoList = listBasicInfo;
        this.context = context;
    }

    @Override
    public int getCount() {
        return deviceInfoList == null ? 0 : deviceInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return Optional.of(deviceInfoList.get(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Component getComponent(int position, Component component, ComponentContainer componentContainer) {
        ViewHolder viewHolder = null;
        Component mComponent = component;
        if (mComponent == null) {
            mComponent = LayoutScatter.getInstance(context).parse(ResourceTable.Layout_item_device_list, null, false);
            viewHolder = new ViewHolder();
            if (mComponent.findComponentById(ResourceTable.Id_device_name) instanceof Text) {
                viewHolder.devicesName = (Text) mComponent.findComponentById(ResourceTable.Id_device_name);
            }
            if (mComponent.findComponentById(ResourceTable.Id_device_id) instanceof Text) {
                viewHolder.devicesId = (Text) mComponent.findComponentById(ResourceTable.Id_device_id);
            }
            mComponent.setTag(viewHolder);
        } else {
            if (mComponent.getTag() instanceof ViewHolder) {
                viewHolder = (ViewHolder) mComponent.getTag();
            }
        }
        if (viewHolder != null) {
            viewHolder.devicesName.setText(deviceInfoList.get(position).getDeviceName());
            String deviceID = deviceInfoList.get(position).getDeviceId();
            deviceID = deviceID.substring(0, 4) + "******" + deviceID.substring(deviceID.length() - 4);
            viewHolder.devicesId.setText(deviceID);
        }
        return mComponent;
    }

    private static class ViewHolder {
        private Text devicesName;
        private Text devicesId;
    }
}
