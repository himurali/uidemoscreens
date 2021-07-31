package com.mahakuta.lalbagh;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.mahakuta.lalbagh.databinding.FragmentFirstBinding;
import com.mahakuta.lalbagh.model.TreeInfo;
import com.mahakuta.lalbagh.ui.TreeDescriptionActivity;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                /*NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);*/

                Intent intent = new Intent(getActivity().getApplicationContext(), TreeDescriptionActivity.class);
                TreeInfo treeInfo = new TreeInfo();
                treeInfo.setTitle("Mangifera Indica");
                treeInfo.setDescription("Mangifera indica, commonly known as mango, is a species of flowering plant in the sumac and poison ivy family Anacardiaceae. Mangoes are believed to have originated from the region between northwestern Myanmar, Bangladesh, and northeastern India. It is a large fruit-tree, capable of growing to a height and crown width of about 30 metres (100 ft) and trunk circumference of more than 3.7 metres (12 ft).");

                treeInfo.setWhereItGrows("India,Bangladesh, Srilanka");


                treeInfo.setTopimage("https://vc474.pcloud.com/dpZu4RqA1Z6FDrdPZoNa47ZZF6DDv7ZNVZZ8uXZXZfRReQqfkUn7zMkLBIG7TP45pmx77/th-31858707641-275x183.jpg");
                treeInfo.setTreeCompleteUrl(treeInfo.getTopimage());

                treeInfo.setLeaves("The leaves are ");
                treeInfo.setLeavesUrl("https://vc652.pcloud.com/dpZ1FRqA1ZdwfrdPZoNa47ZZT6DDv7ZNVZZ8uXZXZB55stAYuUdVp2nwA9STjtYdcqUwX/th-31858690985-1280x853.jpg");

                treeInfo.setFlowers("Flowers appear at the end of winter and beginning of spring.    In India, flowering starts in December in the South, in January in Bihar and Bengal, in February in eastern Uttar Pradesh, and in February–March in northern India.   flower opening is completed by February.   ");
                treeInfo.setFlowersUrl("https://c326.pcloud.com/dpZ94RqA1ZBg1rdPZoNa47ZZv6DDv7ZNVZZ8uXZXZs7JJwTzQ0eh4sEXqEvRgy09eAj1k/th-31858713049-275x183.jpg");

                treeInfo.setFruits("The mango is an irregular, egg-shaped fruit which is a fleshy drupe. The fruits can be round, oval, heart, or kidney shaped. Mango fruits are green when they are unripe.   ");
                treeInfo.setFruitsUrl("https://c379.pcloud.com/dpZMYRqA1Z8x2rdPZoNa47ZZcDxDv7ZNVZZ8uXZXZdfJSYQ0xTVSsgPyn8eVT6zDLTaek/th-31858698338-1000x1000.jpg");


                treeInfo.setUses("The wood is used for musical instruments such as ukuleles,[14] plywood and low-cost furniture");
                Bundle bundle = new Bundle();
                bundle.putSerializable("CLICKED_TREE", treeInfo);
                intent.putExtras(bundle);

                startActivity(intent);


            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}