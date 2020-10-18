import DeviceInfo from "./model/DeviceInfo";

function generateData() {

    var sampleSenders = [
        new DeviceInfo(1, "Sender 1", "1:23:456:789", 0, "123:456", 480, ["one", "onee"]), // t
        new DeviceInfo(2, "Sender 2", "1:32:456:789", 1, "132:456", 480, ["two", "twoo"]), // t
        new DeviceInfo(3, "Sender 3", "1:42:356:789", 2, "142:456", 480, ["three", "threee"]),
        new DeviceInfo(4, "Sender 4", "1:52:356:789", 3, "152:456", 480, ["four", "fourr"]), //
        new DeviceInfo(1, "Sender 5", "1:24:456:789", 0, "123:456", 480, ["one", "onee"]), // t
        new DeviceInfo(2, "Sender 6", "1:33:456:789", 1, "132:456", 480, ["two", "twoo"]), // t
        new DeviceInfo(3, "Sender 7", "1:43:356:789", 2, "142:456", 480, ["three", "threee"]),
        new DeviceInfo(4, "Sender 8", "1:52:356:789", 3, "152:456", 480, ["four", "fourr"]), //
        new DeviceInfo(1, "Sender 9", "1:25:456:789", 0, "123:456", 480, ["one", "onee"]), // t
        new DeviceInfo(2, "Sender 10", "1:35:456:789", 1, "132:456", 480, ["two", "twoo"]), // t
    ];

    var sampleReceivers = [
        new DeviceInfo(1, "receiver 1", "1:23:456:789", 0, "123:456", 480, ["one", "onee"]), // t
        new DeviceInfo(2, "receiver 2", "1:32:456:789", 1, "132:456", 480, ["two", "twoo"]), // t
        new DeviceInfo(3, "receiver 3", "1:42:356:789", 2, "142:456", 480, ["three", "threee"]),
        new DeviceInfo(4, "receiver 4", "1:52:356:789", 3, "152:456", 480, ["four", "fourr"]), //
        new DeviceInfo(1, "receiver 1", "1:24:456:789", 0, "123:456", 480, ["one", "onee"]), // t
        new DeviceInfo(2, "receiver 2", "1:33:456:789", 1, "132:456", 480, ["two", "twoo"]), // t
        new DeviceInfo(3, "receiver 3", "1:43:356:789", 2, "142:456", 480, ["three", "threee"]),
        new DeviceInfo(4, "receiver 4", "1:53:356:789", 3, "152:456", 480, ["four", "fourr"]), //
        new DeviceInfo(1, "receiver 1", "1:24:456:789", 0, "123:456", 480, ["one", "onee"]), // t
        new DeviceInfo(2, "receiver 2", "1:34:456:789", 1, "132:456", 480, ["two", "twoo"]), // t
        new DeviceInfo(3, "receiver 3", "1:44:356:789", 2, "142:456", 480, ["three", "threee"]),
        new DeviceInfo(4, "receiver 4", "1:54:356:789", 3, "152:456", 480, ["four", "fourr"]), //
        new DeviceInfo(1, "receiver 1", "1:25:456:789", 0, "123:456", 480, ["one", "onee"]), // t
        new DeviceInfo(2, "receiver 2", "1:35:456:789", 1, "132:456", 480, ["two", "twoo"]), // t
        new DeviceInfo(3, "receiver 3", "1:45:356:789", 2, "142:456", 480, ["three", "threee"]),
        new DeviceInfo(4, "receiver 4", "1:55:356:789", 3, "152:456", 480, ["four", "fourr"]), //
        new DeviceInfo(1, "receiver 1", "1:26:456:789", 0, "123:456", 480, ["one", "onee"]), // t
        new DeviceInfo(2, "receiver 2", "1:36:456:789", 1, "132:456", 480, ["two", "twoo"]), // t
        new DeviceInfo(3, "receiver 3", "1:46:356:789", 2, "142:456", 480, ["three", "threee"]),
        new DeviceInfo(4, "receiver 4", "1:57:356:789", 3, "152:456", 480, ["four", "fourr"]) // 
    ];

    return (
        {senders: sampleSenders, receivers: sampleReceivers}
    );

}

export default generateData;