import {
    convertToLocal,
    convertToAxios
} from '../ConvertDataFormat'
import DeviceInfo from '../../model/DeviceInfo'
import {
    sampleInputChannels,
    sampleOutputChannels
 } from '../../api/SampleData'

let sampleLocalSender = new DeviceInfo(
    "test sender serial",
    null,
    "test sender ip",
    "test sender display",
    "offline",
    sampleOutputChannels,
    ["Additional Device Info goes here."]
);
let sampleLocalReceiver = new DeviceInfo(
    "test receiver serial",
    null,
    "test receiver ip",
    "test receiver display",
    "offline",
    sampleInputChannels,
    ["Additional Device Info goes here."]
);
let sampleAxiosSender = {
    "serialNumber": "test sender serial",
    "lastCommunication": null,
    "device": {
        "serialNumber": "test sender serial",
        "ipAddress": "test sender ip",
        "displayName": "test sender display",
        "status": "offline"
    },
    "outputs": sampleOutputChannels
};
let sampleAxiosReceiver = {
    "serialNumber": "test receiver serial",
    "lastCommunication": null,
    "device": {
        "serialNumber": "test receiver serial",
        "ipAddress": "test receiver ip",
        "displayName": "test receiver display",
        "status": "offline"
    },
    "inputs": sampleInputChannels
};

test('ConvertToLocal returns DeviceInfo object with correct data', () => {
    var axiosSenderToLocal = JSON.stringify(convertToLocal(sampleAxiosSender));
    var axiosReceiverToLocal = JSON.stringify(convertToLocal(sampleAxiosReceiver));

    expect(axiosSenderToLocal).toStrictEqual(JSON.stringify(sampleLocalSender));
    expect(axiosReceiverToLocal).toStrictEqual(JSON.stringify(sampleLocalReceiver));
})

test('ConvertToAxios returns information in response format with correct data', () => {
    var localSenderToAxios = JSON.stringify(convertToAxios(sampleLocalSender));
    var localReceiverToAxios = JSON.stringify(convertToAxios(sampleLocalReceiver));

    expect(localSenderToAxios).toStrictEqual(JSON.stringify(sampleAxiosSender));
    expect(localReceiverToAxios).toStrictEqual(JSON.stringify(sampleAxiosReceiver));
})