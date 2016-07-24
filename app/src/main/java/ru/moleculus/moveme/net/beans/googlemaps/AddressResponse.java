package ru.moleculus.moveme.net.beans.googlemaps;

/**
 * Created by Oleg on 02.04.2016.
 */
public class AddressResponse extends BaseMapsResponse {

    private static final String TYPE_CITY = "locality";
    private static final String TYPE_STREET = "route";
    private static final String TYPE_STREET_NUMBER = "street_number";

    private Result[] results;

    public String getCity() {
        return getStringResult(TYPE_CITY);
    }

    public String getStreet() {
        return getStringResult(TYPE_STREET);
    }


    public String getStreetNumber() {
        return getStringResult(TYPE_STREET_NUMBER);
    }

    public String getFormattedAddress() {
        String address = "";
        if (getCity() != null) {
            address += getCity();
            if (getStreet() != null) {
                address += (", " + getStreet());
                if (getStreetNumber() != null)
                    address += ", " + getStreetNumber();
            }
        }

        return address;
    }

    private String getStringResult(String type) {
        String string = null;
        if (results != null && results.length > 0) {
            string = results[0].getStringResult(type);
        }
        return string;
    }

    private class Result {

        private AddressComponent[] address_components;

        public String getStringResult(String type) {
            String result = null;
            if (address_components != null)
                for (AddressComponent component : address_components) {
                    for (String componentType : component.getTypes())
                        if (componentType.equals(type))
                            result = component.getLongName();
                }
            return result;
        }

        private class AddressComponent {

            private String long_name;
            private String[] types;

            public String getLongName() {
                return long_name;
            }

            public String[] getTypes() {
                return types;
            }
        }
    }
}
