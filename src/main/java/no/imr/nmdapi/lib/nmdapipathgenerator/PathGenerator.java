package no.imr.nmdapi.lib.nmdapipathgenerator;

import java.io.File;
import java.util.Map;

/**
 * Class used for generating correct paths for data stored behind the API
 *
 * @author sjurl
 */
public class PathGenerator {

    /**
     * Generates the correct path and filename for a mission based on the input
     * information
     *
     * @param basePath
     * @param missionType
     * @param startYear
     * @param platformPath
     * @param delivery
     * @param dataType
     * @return
     */
    public File generatePath(String basePath, String missionType, String startYear, String platformPath, String delivery, String dataType) {
        String path = basePath + File.separator
                + missionType + File.separator
                + startYear + File.separator
                + platformPath + File.separator
                + delivery;

        if (dataType != null) {
            path = path + File.separator + dataType;
        }
        File directory = new File(path);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        return new File(directory, "data.xml");
    }

    /**
     * Takes a map of <String, TypeValue> where the key is the name of the
     * platform code type and the TypeValue's value attribute contains the
     * actual database value for this platform code type. Returns the correctly
     * formatted platform path that is used when generating the file structure.
     *
     * @param platformMap
     * @return
     */
    public String createPlatformURICode(Map<String, TypeValue> platformMap) {

        String shipName = null;
        String callSign = null;
        String result;

        if (platformMap.containsKey("Ship Name")) {
            shipName = platformMap.get("Ship Name").getValue();
            shipName = shipName.replace('.', ' ').replace(File.separator, " ");
        }
        if (platformMap.containsKey("ITU Call Sign")) {
            callSign = platformMap.get("ITU Call Sign").getValue();
            callSign = callSign.replace('.', ' ').replace(File.separator, " ");
        }
        if (shipName != null) {
            if (callSign != null) {
                result = shipName + "-" + callSign;
            } else {
                result = shipName;
            }
        } else if (callSign != null) {
            result = callSign;
        } else {
            result = null;
        }

        return result;
    }
}
