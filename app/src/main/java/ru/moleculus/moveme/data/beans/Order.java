package ru.moleculus.moveme.data.beans;

import com.noisyz.customeelements.utils.SimpleDateUtils;
import com.noisyz.customeelements.utils.SimpleTextUtils;
import com.noisyz.databindinglibrary.annotations.converters.Conversion;
import com.noisyz.databindinglibrary.annotations.field.SimpleAdapterViewField;
import com.noisyz.databindinglibrary.annotations.field.SimpleFieldType;
import com.noisyz.databindinglibrary.annotations.methods.GetterMethod;
import com.noisyz.databindinglibrary.annotations.type;
import com.noisyz.databindinglibrary.utils.ReflectionUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import ru.moleculus.moveme.BaseConstants;
import ru.moleculus.moveme.R;

/**
 * Created by Oleg on 11.02.2016.
 */
public class Order implements Serializable, BaseMoveMeObject, BaseConstants {
    private long id = -1;

    @SimpleFieldType(value = com.noisyz.databindinglibrary.annotations.type.TEXT,
            twoWayConverter = @Conversion(value = UserFriendlyConverter.class))
    private long datetime;


    private int type;

    @SimpleFieldType(value = com.noisyz.databindinglibrary.annotations.type.TEXT)
    private String evac_weight, pass_auto_type, tariff_after_2hours,
            tariff_first_2hours, tariff_out_of_mkad_per_km;

    @SimpleAdapterViewField(resourceArray = R.array.pass_num, indent = 1, layoutResID = R.layout.list_item)
    private int pass_num;

    @SimpleAdapterViewField(indent = 1, layoutResID = R.layout.list_item,
            resourceArray = R.array.pass_vehicle_type)
    private int pass_vehicle_type;

    @SimpleAdapterViewField(indent = 1, layoutResID = R.layout.list_item,
            resourceArray = R.array.pass_vehicle_class)
    private int pass_vehicle_class;

    @SimpleAdapterViewField(layoutResID = R.layout.list_item,
            resourceArray = R.array.evac_vehicle_type)
    private int evac_vehicle_type;

    @SimpleAdapterViewField(layoutResID = R.layout.list_item,
            resourceArray = R.array.evac_vehicle_cond)
    private int evac_vehicle_cond;

    @SimpleAdapterViewField(layoutResID = R.layout.list_item,
            resourceArray = R.array.evac_vehicle_wheels)
    private int evac_vehicle_wheels;

    @SimpleAdapterViewField(layoutResID = R.layout.list_item,
            resourceArray = R.array.packaging_type)
    private int packaging_type;

    @SimpleFieldType(com.noisyz.databindinglibrary.annotations.type.TEXT)
    private String title;
    private ArrayList<String> pics;

    @SimpleFieldType(com.noisyz.databindinglibrary.annotations.type.BOOLEAN)
    private boolean ttk_entrance, center_entrance, hydrobort, hydrolift, sideloader, sealing,
            children, toilet, ev_no_tow_rope, ev_ditch, ev_upside_down, wedding,
            driver_russian, attorney_letter, ev_low_ride, columns;

    private ArrayList<Location> locations;

    public Order() {
        locations = new ArrayList<>();
        cargo = new ArrayList<>();
        pics = new ArrayList<>();
        datetime = System.currentTimeMillis() / 1000L;
    }

    @GetterMethod(value = com.noisyz.databindinglibrary.annotations.type.TEXT, propertyKey = "dateTime")
    public String getFormattedDateTime() {
        return SimpleDateUtils.formatToYesterdayOrToday(getDateTime());
    }

    @GetterMethod(value = com.noisyz.databindinglibrary.annotations.type.TEXT, propertyKey = "start")
    public String getStart() {
        return getLocations().get(0).getAddress();
    }

    @GetterMethod(value = com.noisyz.databindinglibrary.annotations.type.TEXT, propertyKey = "end")
    public String getEnd() {
        return getLocations().get(getLocations().size() - 1).getAddress();
    }

    @GetterMethod(value = com.noisyz.databindinglibrary.annotations.type.BOOLEAN, propertyKey = "isPass")
    public boolean isPassOrder() {
        return type == ORDER_TYPE_PASS;
    }

    @GetterMethod(value = com.noisyz.databindinglibrary.annotations.type.BOOLEAN, propertyKey = "isCargo")
    public boolean isCargoOrder() {
        return type == ORDER_TYPE_CARGO;
    }

    @GetterMethod(value = com.noisyz.databindinglibrary.annotations.type.BOOLEAN, propertyKey = "isEvac")
    public boolean isEvacOrder() {
        return type == ORDER_TYPE_EVAC;
    }

    private ArrayList<Cargo> cargo;

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public void updateDateTime() {
        datetime = System.currentTimeMillis() / 1000L;
    }

    public int getType() {
        return type;
    }

    public ArrayList<Cargo> getCargo() {
        return cargo;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public long getDateTime() {
        return datetime;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<String> getPics() {
        return pics;
    }

    public boolean isColumns() {
        return columns;
    }

    public boolean isTtkEntrance() {
        return ttk_entrance;
    }

    public boolean isCenterEntrance() {
        return center_entrance;
    }

    public boolean isHydroBort() {
        return hydrobort;
    }

    public boolean isHydroLift() {
        return hydrolift;
    }

    public boolean isSideLoader() {
        return sideloader;
    }

    public boolean isSealing() {
        return sealing;
    }

    public boolean isChildren() {
        return children;
    }

    public boolean isToilet() {
        return toilet;
    }

    public boolean isNoTowRope() {
        return ev_no_tow_rope;
    }

    public boolean isDitch() {
        return ev_ditch;
    }

    public boolean isUpsideDown() {
        return ev_upside_down;
    }

    public boolean isWedding() {
        return wedding;
    }

    public boolean isDriverRussian() {
        return driver_russian;
    }

    public boolean isAttorneyLetter() {
        return attorney_letter;
    }

    public boolean isLowRide() {
        return ev_low_ride;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTariffFirst2Hours() {
        return tariff_first_2hours;
    }

    public String getTariffAfter2Hours() {
        return tariff_after_2hours;
    }

    public String getTariffOutOfMkad() {
        return tariff_out_of_mkad_per_km;
    }

    @Override
    public boolean isObjectValid() {
        boolean isValid = true;
        isValid &= locations.size() >= 2;
        for (Location location : locations) {
            isValid &= location.isObjectValid();
        }
        if (type == ORDER_TYPE_CARGO) {
            isValid &= cargo.size() >= 1;
            for (Cargo cargoObject : cargo) {
                isValid &= cargoObject.isObjectValid();
            }
        }
        isValid &= datetime != 0;
        return isValid;
    }

    @Override
    public HashMap<String, String> getRequestHashMap(int objectIndex) {
        HashMap<String, String> hashMap = new HashMap<>();
        for (Field field : getClass().getDeclaredFields()) {
            String value = String.valueOf(ReflectionUtils.getVariableValue(field, this));
            boolean notEmptyId = !(field.getName().equals("id"));
            boolean notArray = !field.getName().equals("locations") &&
                    !field.getName().equals("cargo") && !field.getName().equals("pics");
            if (!SimpleTextUtils.isFieldEmpty(value) && notEmptyId && notArray) {
                hashMap.put(field.getName(), value);
            }
            putPics(hashMap);
        }
        if (cargo != null)
            for (int i = 0; i < cargo.size(); i++) {
                if (cargo.get(i).isObjectValid())
                    hashMap.putAll(cargo.get(i).getRequestHashMap(i));
            }
        if (locations != null)
            for (int i = 0; i < locations.size(); i++)
                if (locations.get(i).isObjectValid())
                    hashMap.putAll(locations.get(i).getRequestHashMap(i));
        return hashMap;
    }

    public void putPics(HashMap<String, String> map) {
        if (pics != null && !pics.isEmpty())
            for (int i = 0; i < pics.size(); i++)
                map.put("pics[" + i + "]", pics.get(i));
    }
}
