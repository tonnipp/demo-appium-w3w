package data;

import helper.JSONHelper;
import org.testng.annotations.DataProvider;

public class TestData {

    @DataProvider(name = "validSearchData")
    public static Object[][] validSearchData() {

        return JSONHelper.ReadJsonFromFile(
                "src/test/resources/ValidSearchData.json");
    }

    @DataProvider(name = "invalidSearchData")
    public static Object[][] invalidSearchData() {

        return JSONHelper.ReadJsonFromFile(
                "src/test/resources/InvalidSearchData.json");
    }
    @DataProvider
    public static Object[][] sequentialSearchData() {

        return JSONHelper.ReadJsonFromFile(
                "src/test/resources/SequentialSearchData.json");
    }
    @DataProvider(name = "recoverySearchData")
    public static Object[][] recoverySearchData() {

        Object[][] validData =
                JSONHelper.ReadJsonFromFile(
                        "src/test/resources/ValidSearchData.json");

        Object[][] invalidData =
                JSONHelper.ReadJsonFromFile(
                        "src/test/resources/InvalidSearchData.json");

        Object[][] result =
                new Object[invalidData.length * validData.length][2];

        int index = 0;

        for (Object[] invalid : invalidData) {

            for (Object[] valid : validData) {

                result[index][0] = invalid[0];
                result[index][1] = valid[0];

                index++;
            }
        }

        return result;
    }
    public static String getFirstValidSearch() {

        Object[][] data =
                validSearchData();

        return (String) data[0][0];
    }
}