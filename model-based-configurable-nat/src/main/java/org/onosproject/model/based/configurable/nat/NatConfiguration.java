package org.onosproject.model.based.configurable.nat;


import java.io.*;

/**
 * Created by gabriele on 22/07/16.
 */

public class NatConfiguration {

    private static final String CONFIGURATION_FILE = "configuration/nat-config.ini";

    private static final String INTERFACES = "interfaces";
    private static final String ADDRESSES = "addresses";
    private static final String PORT_LABELS = "port_labels";

    private String privatePortLabel;
    private String publicPortLabel;

    private String userDeviceId;
    private String wanDeviceId;
    private String userInterface;
    private String wanInterface;

    private String privateAddress;
    private String publicAddress;

    public NatConfiguration() throws IOException {

        // [port_labels]
        privatePortLabel = iniLoad(PORT_LABELS, "private_port_label");
        publicPortLabel = iniLoad(PORT_LABELS, "public_port_label");

        // [interfaces]
        userDeviceId = iniLoad(INTERFACES, "user_device");
        wanDeviceId = iniLoad(INTERFACES, "wan_device");
        userInterface = iniLoad(INTERFACES, "user_interface");
        wanInterface = iniLoad(INTERFACES, "wan_interface");

        // [addresses]
        privateAddress = iniLoad(ADDRESSES, "private_address");
        publicAddress = iniLoad(ADDRESSES, "public_address");
    }

    public String getPrivatePortLabel() {
        return privatePortLabel;
    }

    public String getPublicPortLabel() {
        return publicPortLabel;
    }


    public String getUserDeviceId() {
        return userDeviceId;
    }

    public String getWanDeviceId() {
        return wanDeviceId;
    }

    public String getUserInterface() {
        return userInterface;
    }

    public String getWanInterface() {
        return wanInterface;
    }

    public String getPrivateAddress() {
        return privateAddress;
    }

    public String getPublicAddress() {
        return publicAddress;
    }

    private String iniLoad(String section, String key) throws IOException {

        ClassLoader classLoader = AppComponent.class.getClassLoader();
        InputStream is = classLoader.getResourceAsStream(CONFIGURATION_FILE);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        //File file = new File(classLoader.getResource("configuration/nat-config.ini").getFile());
        //BufferedReader br = new BufferedReader(new FileReader(file));

        String line;
        do {
            line = br.readLine();
        } while (line != null && !line.equals("["+section+"]"));

        if (line == null)
            throw new RuntimeException("Section '" + section + "' not found in configuration file.");

        do {
            line = br.readLine();
        } while (line != null && (line.equals("") || (!(line.charAt(0) == '[') && !line.split("=")[0].replaceAll(" ", "").equals(key))));

        if (line == null || line.charAt(0) == '[')
            throw new RuntimeException("Key '" + key + "' not found in section '" + section + "' of configuration file.");

        System.out.println("Found line: " + line);
        return line.split("=")[1].replaceAll(" ", "");
    }
}

