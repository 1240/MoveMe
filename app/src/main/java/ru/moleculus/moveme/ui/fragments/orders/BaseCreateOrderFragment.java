package ru.moleculus.moveme.ui.fragments.orders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.noisyz.customeelements.dialog.AlertDialogFactory;
import com.noisyz.customeelements.dialog.SetDateDialogListener;
import com.noisyz.customeelements.utils.SimpleDateUtils;
import com.noisyz.customeelements.utils.SimpleImageUtils;
import com.noisyz.customeelements.utils.SimpleViewUtils;
import com.noisyz.databindinglibrary.annotations.field.SimpleFieldType;
import com.noisyz.databindinglibrary.bind.base.impl.adapter.BindAdapter;
import com.noisyz.databindinglibrary.bind.base.impl.adapter.ConvertBindAdapter;
import com.noisyz.databindinglibrary.bind.base.impl.ObjectDataBinder;
import com.noisyz.databindinglibrary.bind.base.UIBinder;

import java.io.File;

import ru.moleculus.moveme.R;
import ru.moleculus.moveme.adapters.LocationListAdapter;
import ru.moleculus.moveme.customview.MoveMeImageGallery;
import ru.moleculus.moveme.data.beans.Cargo;
import ru.moleculus.moveme.data.beans.Location;
import ru.moleculus.moveme.data.beans.Order;
import ru.moleculus.moveme.presenters.CreateOrderPresenter;
import ru.moleculus.moveme.presenters.impl.CreateOrderPresenterImpl;
import ru.moleculus.moveme.ui.activity.CreateTemplateActivity;
import ru.moleculus.moveme.ui.activity.SetCargoActivity;
import ru.moleculus.moveme.ui.activity.SetLocationActivity;
import ru.moleculus.moveme.ui.activity.ShowImageActivity;
import ru.moleculus.moveme.ui.fragments.user.AbsUserFragment;
import ru.moleculus.moveme.view.CreateOrderView;

/**
 * Created by Oleg on 08.03.2016.
 */
public class BaseCreateOrderFragment extends AbsUserFragment implements CreateOrderView, View.OnClickListener {

    private int type, selectedLocationPosition, selectedCargoPosition;
    private Order order;
    private CreateOrderPresenter createOrderPresenter;

    @SimpleFieldType(com.noisyz.databindinglibrary.annotations.type.BOOLEAN)
    private boolean needSavingPrice;

    private BindAdapter.OnItemClickListener<Location> onLocationClickListener = new
            ConvertBindAdapter.OnItemClickListener<Location>() {
                @Override
                public void onItemClick(int position, Location o) {
                    Intent intent = new Intent(getContext(), SetLocationActivity.class);
                    intent.putExtra(EXTRA_LOCATION, o);
                    intent.putExtra(EXTRA_POSITION, position);
                    selectedLocationPosition = position;
                    startActivityForResult(intent, LOCATION_REQUEST_CODE);
                }
            };

    private BindAdapter.OnItemClickListener<Cargo> onCargoClickListener = new ConvertBindAdapter.OnItemClickListener<Cargo>() {
        @Override
        public void onItemClick(int position, Cargo o) {
            Intent intent = new Intent(getContext(), SetCargoActivity.class);
            intent.putExtra(EXTRA_CARGO, o);
            intent.putExtra(EXTRA_POSITION, position);
            selectedCargoPosition = position;
            startActivityForResult(intent, CARGO_REQUEST_CODE);
        }
    };

    public static BaseCreateOrderFragment newInstance(int type) {
        Bundle arguments = new Bundle();
        arguments.putInt(EXTRA_ORDER_TYPE, type);
        BaseCreateOrderFragment fragment = new BaseCreateOrderFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    public static BaseCreateOrderFragment newInstance(Order order, int type) {
        Bundle arguments = new Bundle();
        arguments.putInt(EXTRA_ORDER_TYPE, type);
        arguments.putSerializable(EXTRA_ORDER, order);
        BaseCreateOrderFragment fragment = new BaseCreateOrderFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    public BaseCreateOrderFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle args = getArguments();
            type = args.getInt(EXTRA_ORDER_TYPE);
            order = (Order) args.getSerializable(EXTRA_ORDER);
            if (order == null) {
                order = new Order();
            }
            order.setType(type);
        }
        createOrderPresenter = new CreateOrderPresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_new_order_fragment, null);
        initAddressListView(view);
        View orderTypeView = getOrderTypeView(type);
        ((FrameLayout) view.findViewById(R.id.order_type_container)).
                addView(orderTypeView);
        view.findViewById(R.id.ll_date).setOnClickListener(this);
        view.findViewById(R.id.btn_order_create).setOnClickListener(this);
        getDataBinder().newBinder("OrderCreateUI", new ObjectDataBinder<>(order).
                registerView(view)).newBinder("needSaving",
                new ObjectDataBinder<>(this).
                        registerView(view))
                .bindUI();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_order_create).setEnabled(false);
    }

    private View getOrderTypeView(int type) {
        View view = null;
        switch (type) {
            case ORDER_TYPE_CARGO:
                view = View.inflate(getContext(), R.layout.new_cargo_order_fragment, null);
                initCargoListView(view);
                break;
            case ORDER_TYPE_EVAC:
                view = View.inflate(getContext(), R.layout.new_evac_order_fragment, null);
                initGallery(view);
                break;
            case ORDER_TYPE_PASS:
                view = View.inflate(getContext(), R.layout.new_pass_order_fragment, null);
                break;
        }
        return view;
    }

    private void initAddressListView(View view) {
        ListView listView = (ListView) view.findViewById(R.id.address_list_view);
        listView.performClick();
        View footer = view.findViewById(R.id.address_list_footer);
        final ConvertBindAdapter<Location> adapter = new LocationListAdapter(getContext(), order.getLocations())
                .setOnItemClickListener(onLocationClickListener);
        getDataBinder().newBinder("locationsList", adapter);
        listView.setAdapter(adapter);
        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLocationClickListener.onItemClick(adapter.getCount(), null);
            }
        });
        adapter.bindUI();
        SimpleViewUtils.setListViewHeightBasedOnChildren(listView);
        adapter.bindUI();
    }

    private void initCargoListView(View view) {
        ListView listView = (ListView) view.findViewById(R.id.cargo_list_view);
        listView.performClick();
        View footer = view.findViewById(R.id.cargo_list_footer);
        final BindAdapter<Cargo> adapter = new BindAdapter<>(getContext(), order.getCargo(), R.layout.cargo_list_item)
                .setOnItemClickListener(onCargoClickListener);
        getDataBinder().newBinder("cargoList", adapter);
        listView.setAdapter(adapter);
        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCargoClickListener.onItemClick(adapter.getCount(), null);
            }
        });
        adapter.bindUI();
        SimpleViewUtils.setListViewHeightBasedOnChildren(listView);
    }

    private void initGallery(View view) {
        MoveMeImageGallery gallery = (MoveMeImageGallery) view.findViewById(R.id.gallery);
        gallery.setImageUrls(order.getPics());
        getDataBinder().newBinder("galleryBinder", gallery.getAdapter());
        gallery.setOnAddItemClickListener(new MoveMeImageGallery.OnItemClickListener() {
            @Override
            public void onClickAddImage() {
                AlertDialogFactory.initDialogPickImage(BaseCreateOrderFragment.this).show();
            }

            @Override
            public void onClickShowImage(String url) {
                Intent intent = new Intent(getActivity(), ShowImageActivity.class);
                intent.putExtra(ShowImageActivity.EXTRA_IMAGE, url);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        SimpleImageUtils.handleOnImagePickActivityResult(getContext(), requestCode, resultCode, data,
                new SimpleImageUtils.OnImagePickedCallback() {
                    @Override
                    public void onImagePicked(File file) {
                        sendUserFile(file);
                    }
                });

        handleLocationOnActivityResult(requestCode, resultCode, data);

        handleCargoOnActivityResult(requestCode, resultCode, data);

        getDataBinder().bindUI();
    }

    private void handleLocationOnActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Location location = (Location) data.getExtras().getSerializable(SetLocationActivity.EXTRA_LOCATION);
                if (selectedLocationPosition >= order.getLocations().size()) {
                    order.getLocations().add(location);
                } else {
                    order.getLocations().set(selectedLocationPosition, location);
                }
            } else if (resultCode == RESULT_DELETE && order.getLocations().size() > 2
                    && order.getLocations().size() > selectedLocationPosition) {
                order.getLocations().remove(selectedLocationPosition);
            }
            SimpleViewUtils.setListViewHeightBasedOnChildren(
                    (ListView) getView().findViewById(R.id.address_list_view));
        }
    }

    private void handleCargoOnActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CARGO_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Cargo cargo = (Cargo) data.getExtras().getSerializable(SetLocationActivity.EXTRA_CARGO);
                if (selectedCargoPosition >= order.getCargo().size()) {
                    order.getCargo().add(cargo);
                } else {
                    order.getCargo().set(selectedCargoPosition, cargo);
                }
            } else if (resultCode == RESULT_DELETE && order.getCargo().size() > 1
                    && order.getCargo().size() > selectedCargoPosition) {
                order.getCargo().remove(selectedCargoPosition);
            }
            SimpleViewUtils.setListViewHeightBasedOnChildren(
                    (ListView) getView().findViewById(R.id.cargo_list_view));
        }
    }

    @Override
    public void onFileSent(String url) {
        order.getPics().add(url);
        getDataBinder().bindUI();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_date:
                AlertDialogFactory.getSetDateDialogWithCurrentDate(getContext(), new SetDateDialogListener() {
                    @Override
                    public void fillTextField(int fieldId, long time) {
                        ((TextView) getView().findViewById(R.id.tv_date)).setText(
                                SimpleDateUtils.ORDERS_DATE_FORMAT.format(time)
                        );
                    }
                }).show(R.id.tv_date);
                break;
            case R.id.btn_order_create:
                sendOrder(order);
                break;
        }
    }

    @Override
    public void onDataUpdated(UIBinder uiBinder, Object object, String propertyNaem, Object value) {
        if (getView() != null) {
            getView().findViewById(R.id.btn_order_create).setEnabled(order.isObjectValid());
        }
    }

    @Override
    public void sendOrder(Order order) {
        createOrderPresenter.sendOrder(order, needSavingPrice);
    }

    @Override
    public void onOrderCreate(Order order) {
        Intent intent = new Intent(getContext(), CreateTemplateActivity.class);
        intent.putExtra(EXTRA_ID, order.getId());
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onOrderUpdated(Order order) {
        getActivity().finish();
    }
}
