package com.maverick.theme;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;

import com.maverick.R;
import com.maverick.base.ThemeActivity;

import cntv.themelibrary.Theme;

/**
 * Created by limingfei on 2017/12/25.
 */

public class ColorsSetting extends ThemeSetting {

    public ColorsSetting(ThemeActivity activity) {
        super(activity);
    }

    public void chooseBaseTheme() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), getActivity().getDialogStyle());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_choose_base_theme, null);

        View basic_theme_title = view.findViewById(R.id.basic_theme_title);
        CardView basic_theme_card = (CardView) view.findViewById(R.id.basic_theme_card);

        basic_theme_title.setBackgroundColor(getActivity().getPrimaryColor());
        basic_theme_card.setCardBackgroundColor(getActivity().getCardBackgroundColor());

        builder.setView(view);
        final AlertDialog dialog = builder.show();

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.ll_white_basic_theme:
                        getActivity().setBaseTheme(Theme.LIGHT);
                        break;
                    case R.id.ll_dark_basic_theme:
                        getActivity().setBaseTheme(Theme.DARK);
                        break;
                    case R.id.ll_dark_amoled_basic_theme:
                        getActivity().setBaseTheme(Theme.AMOLED);
                        break;
                }
                getActivity().updateUiElements();
                dialog.dismiss();
            }
        };
        view.findViewById(R.id.ll_white_basic_theme).setOnClickListener(listener);
        view.findViewById(R.id.ll_dark_basic_theme).setOnClickListener(listener);
        view.findViewById(R.id.ll_dark_amoled_basic_theme).setOnClickListener(listener);
    }

}
