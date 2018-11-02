package com.example.anacamargos.wisemarket;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class ProductsFragment extends Fragment {


    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Produto> listaDeProdutos;
    SearchView searchView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProductsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductsFragment newInstance(String param1, String param2) {
        ProductsFragment fragment = new ProductsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_products, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerview);
        listaDeProdutos = new ArrayList<Produto>();

        searchView = (SearchView) getView().findViewById(R.id.searchView);
        searchView.setQueryHint("Digite o nome do produto desejado...");
        searchView.setIconifiedByDefault(false);

        Produto produto = new Produto ("Coca-Cola", 5.30);
        Produto produto2 = new Produto ("Cola", 7.50);
        Produto novoproduto = new Produto ( "Arroz", 10.70);
        Produto novoproduto2 = new Produto ( "Ar", 16.70);
        Produto novonovoProduto = new Produto ("Feijão", 15.50);
        listaDeProdutos.add(produto);
        listaDeProdutos.add(novoproduto);
        listaDeProdutos.add(novonovoProduto);
        listaDeProdutos.add(produto2);
        listaDeProdutos.add(novoproduto2);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        final RecyclerViewAdapterProduct recyclerViewAdapter = new RecyclerViewAdapterProduct(getContext(), listaDeProdutos);
        recyclerView.setAdapter(recyclerViewAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Toast.makeText(getBaseContext(), newText, Toast.LENGTH_LONG).show();

                ArrayList<Produto> listaDeProdutosSelecionada = new ArrayList<Produto>();

                for (int i = 0; i < listaDeProdutos.size(); i ++) {
                    Produto atual = listaDeProdutos.get(i);
                    if (atual.getNome().startsWith(newText)) {
                        listaDeProdutosSelecionada.add(atual);
                    }
                }

                layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                final RecyclerViewAdapterProduct recyclerViewAdapter = new RecyclerViewAdapterProduct(getContext(), listaDeProdutosSelecionada);
                recyclerView.setAdapter(recyclerViewAdapter);

                return false;
            }
        });


        recyclerView.addOnItemTouchListener(new RecyclerItemClickListenerProduct(this, recyclerView, new RecyclerItemClickListenerProduct.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Produto produtoClicado = listaDeProdutos.get(position);
                Toast.makeText(getContext(), "Cliquei no produto " + produtoClicado.getNome(), Toast.LENGTH_SHORT ).show();
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));



    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
