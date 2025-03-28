// Generated by view binder compiler. Do not edit!
package com.example.headhunterapps.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public final class FragmentMassageBinding implements ViewBinding {
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
  public final ImageView favourites;

  @NonNull
  public final ImageView home;

  @NonNull
  public final ImageView massage;

  @NonNull
  public final ImageView profile;

  @NonNull
  public final ImageView redTab;

  @NonNull
  public final ImageView send;

  @NonNull
  public final ImageView tab;

  @NonNull
  public final TextView textRed;

  @NonNull
  public final TextView textView;

  private FragmentMassageBinding(@NonNull ConstraintLayout rootView, @NonNull CardView cardView,
      @NonNull CardView cardView2, @NonNull CardView cardView3, @NonNull CardView cardView4,
      @NonNull ImageView favourites, @NonNull ImageView home, @NonNull ImageView massage,
      @NonNull ImageView profile, @NonNull ImageView redTab, @NonNull ImageView send,
      @NonNull ImageView tab, @NonNull TextView textRed, @NonNull TextView textView) {
    this.rootView = rootView;
    this.cardView = cardView;
    this.cardView2 = cardView2;
    this.cardView3 = cardView3;
    this.cardView4 = cardView4;
    this.favourites = favourites;
    this.home = home;
    this.massage = massage;
    this.profile = profile;
    this.redTab = redTab;
    this.send = send;
    this.tab = tab;
    this.textRed = textRed;
    this.textView = textView;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentMassageBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentMassageBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_massage, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentMassageBinding bind(@NonNull View rootView) {
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

      id = R.id.massage;
      ImageView massage = ViewBindings.findChildViewById(rootView, id);
      if (massage == null) {
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

      id = R.id.textView;
      TextView textView = ViewBindings.findChildViewById(rootView, id);
      if (textView == null) {
        break missingId;
      }

      return new FragmentMassageBinding((ConstraintLayout) rootView, cardView, cardView2, cardView3,
          cardView4, favourites, home, massage, profile, redTab, send, tab, textRed, textView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
