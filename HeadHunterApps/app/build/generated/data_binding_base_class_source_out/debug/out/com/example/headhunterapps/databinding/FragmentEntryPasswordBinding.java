// Generated by view binder compiler. Do not edit!
package com.example.headhunterapps.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.headhunterapps.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentEntryPasswordBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final CardView cardView;

  @NonNull
  public final CardView cardView2;

  @NonNull
  public final CardView cardView3;

  @NonNull
  public final CardView cardView4;

  @NonNull
  public final TextView email;

  @NonNull
  public final ImageView favourites;

  @NonNull
  public final ImageView home;

  @NonNull
  public final LinearLayout linearLayout;

  @NonNull
  public final LinearLayout linearLayoutPass;

  @NonNull
  public final ImageView massage;

  @NonNull
  public final EditText passwordInput1;

  @NonNull
  public final EditText passwordInput2;

  @NonNull
  public final EditText passwordInput3;

  @NonNull
  public final EditText passwordInput4;

  @NonNull
  public final TextView passwordText;

  @NonNull
  public final ImageView profile;

  @NonNull
  public final ImageView redTab;

  @NonNull
  public final ImageView send;

  @NonNull
  public final TextView sendEmail;

  @NonNull
  public final ImageView tab;

  @NonNull
  public final TextView textRed;

  private FragmentEntryPasswordBinding(@NonNull ConstraintLayout rootView,
      @NonNull CardView cardView, @NonNull CardView cardView2, @NonNull CardView cardView3,
      @NonNull CardView cardView4, @NonNull TextView email, @NonNull ImageView favourites,
      @NonNull ImageView home, @NonNull LinearLayout linearLayout,
      @NonNull LinearLayout linearLayoutPass, @NonNull ImageView massage,
      @NonNull EditText passwordInput1, @NonNull EditText passwordInput2,
      @NonNull EditText passwordInput3, @NonNull EditText passwordInput4,
      @NonNull TextView passwordText, @NonNull ImageView profile, @NonNull ImageView redTab,
      @NonNull ImageView send, @NonNull TextView sendEmail, @NonNull ImageView tab,
      @NonNull TextView textRed) {
    this.rootView = rootView;
    this.cardView = cardView;
    this.cardView2 = cardView2;
    this.cardView3 = cardView3;
    this.cardView4 = cardView4;
    this.email = email;
    this.favourites = favourites;
    this.home = home;
    this.linearLayout = linearLayout;
    this.linearLayoutPass = linearLayoutPass;
    this.massage = massage;
    this.passwordInput1 = passwordInput1;
    this.passwordInput2 = passwordInput2;
    this.passwordInput3 = passwordInput3;
    this.passwordInput4 = passwordInput4;
    this.passwordText = passwordText;
    this.profile = profile;
    this.redTab = redTab;
    this.send = send;
    this.sendEmail = sendEmail;
    this.tab = tab;
    this.textRed = textRed;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentEntryPasswordBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentEntryPasswordBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_entry_password, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentEntryPasswordBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.cardView;
      CardView cardView = ViewBindings.findChildViewById(rootView, id);
      if (cardView == null) {
        break missingId;
      }

      id = R.id.cardView2;
      CardView cardView2 = ViewBindings.findChildViewById(rootView, id);
      if (cardView2 == null) {
        break missingId;
      }

      id = R.id.cardView3;
      CardView cardView3 = ViewBindings.findChildViewById(rootView, id);
      if (cardView3 == null) {
        break missingId;
      }

      id = R.id.cardView4;
      CardView cardView4 = ViewBindings.findChildViewById(rootView, id);
      if (cardView4 == null) {
        break missingId;
      }

      id = R.id.email;
      TextView email = ViewBindings.findChildViewById(rootView, id);
      if (email == null) {
        break missingId;
      }

      id = R.id.favourites;
      ImageView favourites = ViewBindings.findChildViewById(rootView, id);
      if (favourites == null) {
        break missingId;
      }

      id = R.id.home;
      ImageView home = ViewBindings.findChildViewById(rootView, id);
      if (home == null) {
        break missingId;
      }

      id = R.id.linearLayout;
      LinearLayout linearLayout = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout == null) {
        break missingId;
      }

      id = R.id.linearLayoutPass;
      LinearLayout linearLayoutPass = ViewBindings.findChildViewById(rootView, id);
      if (linearLayoutPass == null) {
        break missingId;
      }

      id = R.id.massage;
      ImageView massage = ViewBindings.findChildViewById(rootView, id);
      if (massage == null) {
        break missingId;
      }

      id = R.id.password_input_1;
      EditText passwordInput1 = ViewBindings.findChildViewById(rootView, id);
      if (passwordInput1 == null) {
        break missingId;
      }

      id = R.id.password_input_2;
      EditText passwordInput2 = ViewBindings.findChildViewById(rootView, id);
      if (passwordInput2 == null) {
        break missingId;
      }

      id = R.id.password_input_3;
      EditText passwordInput3 = ViewBindings.findChildViewById(rootView, id);
      if (passwordInput3 == null) {
        break missingId;
      }

      id = R.id.password_input_4;
      EditText passwordInput4 = ViewBindings.findChildViewById(rootView, id);
      if (passwordInput4 == null) {
        break missingId;
      }

      id = R.id.password_text;
      TextView passwordText = ViewBindings.findChildViewById(rootView, id);
      if (passwordText == null) {
        break missingId;
      }

      id = R.id.profile;
      ImageView profile = ViewBindings.findChildViewById(rootView, id);
      if (profile == null) {
        break missingId;
      }

      id = R.id.red_tab;
      ImageView redTab = ViewBindings.findChildViewById(rootView, id);
      if (redTab == null) {
        break missingId;
      }

      id = R.id.send;
      ImageView send = ViewBindings.findChildViewById(rootView, id);
      if (send == null) {
        break missingId;
      }

      id = R.id.send_email;
      TextView sendEmail = ViewBindings.findChildViewById(rootView, id);
      if (sendEmail == null) {
        break missingId;
      }

      id = R.id.tab;
      ImageView tab = ViewBindings.findChildViewById(rootView, id);
      if (tab == null) {
        break missingId;
      }

      id = R.id.text_red;
      TextView textRed = ViewBindings.findChildViewById(rootView, id);
      if (textRed == null) {
        break missingId;
      }

      return new FragmentEntryPasswordBinding((ConstraintLayout) rootView, cardView, cardView2,
          cardView3, cardView4, email, favourites, home, linearLayout, linearLayoutPass, massage,
          passwordInput1, passwordInput2, passwordInput3, passwordInput4, passwordText, profile,
          redTab, send, sendEmail, tab, textRed);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
