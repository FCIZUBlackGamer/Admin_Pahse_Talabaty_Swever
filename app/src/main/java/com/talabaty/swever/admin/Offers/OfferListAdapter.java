package com.talabaty.swever.admin.Offers;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.talabaty.swever.admin.Mabi3at.Mabi3atNavigator;
import com.talabaty.swever.admin.R;
import com.talabaty.swever.admin.SystemDatabase;
import com.talabaty.swever.admin.SystemPermission;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class OfferListAdapter extends RecyclerView.Adapter<OfferListAdapter.Vholder> {

    Context context;
    List<ListOfferModel> agents;
    int type;

    View Control_view;

    ImageView close, delete, edit;
    String Delete_Link, Edit_Link;
    SystemDatabase systemDatabase;
    Cursor sysCursor;
    List<SystemPermission> permissions;

    public OfferListAdapter(Context context, List<ListOfferModel> agents, int type) {
        this.context = context;
        this.agents = agents;
        this.type = type;
    }

    @NonNull
    @Override
    public Vholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_offer_list, parent, false);
        systemDatabase = new SystemDatabase(context);
        sysCursor = systemDatabase.ShowData();
        permissions = new ArrayList<>();
        while (sysCursor.moveToNext()) {
            SystemPermission systemPermission = new SystemPermission();
            systemPermission.setCreate(Boolean.valueOf(sysCursor.getString(1)));
            systemPermission.setDelete(Boolean.valueOf(sysCursor.getString(2)));
            systemPermission.setUpdate(Boolean.valueOf(sysCursor.getString(4)));
            systemPermission.setScreensId(Integer.parseInt(sysCursor.getString(5)));
            permissions.add(systemPermission);
        }
        return new Vholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vholder holder, final int position) {

        if (type == 2) {
            /** Restaurant */
            Delete_Link = "http://sellsapi.rivile.com/Offers2/Delete";
            Edit_Link = "http://sellsapi.rivile.com/Offers2/EditPage";
        } else if (type == 1) {
            /** Market */
            Delete_Link = "http://sellsapi.rivile.com/Offers1/Delete";
            Edit_Link = "http://sellsapi.rivile.com/Offers1/EditPage";
        } else {
            /** Other */
            Delete_Link = "http://sellsapi.rivile.com/Offers/Delete";
            Edit_Link = "http://sellsapi.rivile.com/Offers/EditPage";
        }

        holder.name.setText(agents.get(position).getName());
        holder.price.setText(agents.get(position).getPrice() + " LE");
        if (agents.get(position).getPhoto() != null || !agents.get(position).getPhoto().isEmpty()) {
            Picasso.with(context).load("http://www.selltlbaty.rivile.com/"+agents.get(position).getPhoto()).into(holder.image);
        }

        holder.action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                Control_view = inflater.inflate(R.layout.control_view, null);

                close = Control_view.findViewById(R.id.close);
                delete = Control_view.findViewById(R.id.delete);
                edit = Control_view.findViewById(R.id.edit);

                if (!permissions.get(1).isUpdate()){
                    edit.setVisibility(View.GONE);
                }

                if (!permissions.get(1).isDelete()){
                    delete.setVisibility(View.GONE);
                }


                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(false)
                        .setView(Control_view);

                final AlertDialog dialog = builder.create();
                dialog.show();

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteOffer(position);
                        dialog.dismiss();
                    }
                });

                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editOffer(position);
                        dialog.dismiss();
                    }
                });

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();

                    }
                });


            }
        });

    }

    private void editOffer(final int position) {
        final ProgressDialog loading = ProgressDialog.show(context, "Getting Offer Info...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Edit_Link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        Log.e("Path: ", s);
                            try {
                                JSONObject object = new JSONObject(s);
                                JSONObject offer = object.getJSONObject("offer");
                                TotalOffer totalOffer = new TotalOffer();
                                totalOffer.setId(offer.getInt("Id"));
                                totalOffer.setName(offer.getString("Name"));
                                totalOffer.setPrice(offer.getDouble("Price"));
                                totalOffer.setPhoto(offer.getString("Photo"));
                                totalOffer.setBlock(offer.getBoolean("Block"));
                                totalOffer.setDescription(offer.getString("Description"));

                                //Todo: Edit Content HEEEEEEEEEEEEEEERE
                                JSONArray array = new JSONArray(offer.getString("OfferTable"));
                                if (array.length() > 0){
                                    List<OperOfferModel> OfferTable = new ArrayList<>();
                                    for (int x=0; x<array.length(); x++){
                                        JSONObject object2 = array.getJSONObject(x);
                                        OperOfferModel model = new OperOfferModel();
                                        model.setId(object2.getInt("Id"));
                                        Log.e("Id",model.getId()+"");
                                        model.setAmount(object2.getInt("Amount"));
                                        model.setName(object2.getString("ProdeuctName"));
                                        model.setOffersId(object2.getInt("OffersId"));
                                        model.setPrice(object2.getDouble("SizePrice"));
                                        model.setSampleProductId(object2.getInt("SampleProductId"));
                                        model.setSizeId(1/*object2.getInt("SizeId")*/);
                                        OfferTable.add(model);
                                    }
                                    totalOffer.setOfferList(OfferTable);
                                }

                                Intent intent = new Intent(context, Mabi3atNavigator.class);
                                if (type == 2) {
                                    /** Restaurant */
                                    intent.putExtra("fragment", "edit_restaurant");
                                } else if (type == 1) {
                                    /** Market */
                                    intent.putExtra("fragment", "edit_market");
                                } else {
                                    /** Other */
                                    intent.putExtra("fragment", "edit_other");
                                }
                                intent.putExtra("Model", totalOffer);
                                context.startActivity(intent);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
//                        Toast.makeText(getActivity(), volleyError.getMessage(), Toast.LENGTH_LONG).show();
                        if (volleyError instanceof ServerError)
                            Log.e("Error: ", "Server Error");
                        else if (volleyError instanceof TimeoutError)
                            Log.e("Error: ", "Timeout Error");
                        else if (volleyError instanceof NetworkError)
                            Log.e("Error: ", "Bad Network");
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put("Id", agents.get(position).getId() + "");

                params.put("token", "bKPNOJrob8x");

                //returning parameters
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                2,  // maxNumRetries = 2 means no retry
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(stringRequest);
    }

    private void deleteOffer(final int position) {
        final ProgressDialog loading = ProgressDialog.show(context, "Delete Offer...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Delete_Link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        Log.e("Path: ", s);
                        if (s.equals("\"Success\"")) {
                            Toast toast = Toast.makeText(context, "تمت العملية بنجاح", Toast.LENGTH_SHORT);
                            TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                            v.setTextColor(Color.GREEN);
                            toast.show();

                            try {
                                if (agents.size() > 0) {
                                    agents.remove(position);
                                    notifyItemRemoved(position);
                                } else {
                                    Toast.makeText(context, "Can't Remove", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception ss) {
//                    Toast.makeText(context,"Can't Remove",Toast.LENGTH_SHORT).show();

                                for (int x = 0; x < agents.size(); x++) {
                                    agents.remove(x);
                                    notifyItemRemoved(x);
                                }
                            }

                        } else {
                            Toast toast = Toast.makeText(context, "عذرا حدث خطأ أثناء اجراء العملية  .. يرجي المحاوله لاحقا", Toast.LENGTH_SHORT);
                            TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                            v.setTextColor(Color.RED);
                            toast.show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
//                        Toast.makeText(getActivity(), volleyError.getMessage(), Toast.LENGTH_LONG).show();
                        if (volleyError instanceof ServerError)
                            Log.e("Error: ", "Server Error");
                        else if (volleyError instanceof TimeoutError)
                            Log.e("Error: ", "Timeout Error");
                        else if (volleyError instanceof NetworkError)
                            Log.e("Error: ", "Bad Network");
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put("Id", agents.get(position).getId() + "");

                params.put("token", "bKPNOJrob8x");

                //returning parameters
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                2,  // maxNumRetries = 2 means no retry
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(stringRequest);
    }

    @Override
    public int getItemCount() {
        return agents.size();
    }

    public class Vholder extends RecyclerView.ViewHolder {
        TextView name, price;
        ImageView image;
        Button action;

        public Vholder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.offer_image);
            action = itemView.findViewById(R.id.action);
            price = itemView.findViewById(R.id.offer_price);
            name = itemView.findViewById(R.id.offer_name);
        }
    }

}
