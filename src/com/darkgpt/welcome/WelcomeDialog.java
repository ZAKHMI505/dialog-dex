package com.darkgpt.welcome;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public final class WelcomeDialog {
  static final int RED = 0xFFE0101E, DARK = 0xFF111116;

  private WelcomeDialog() {}

  public static void show(final Activity a) {
    try {
      new Handler(Looper.getMainLooper()).post(new Runnable() {
        public void run() { try { build(a); } catch (Throwable t) { } }
      });
    } catch (Throwable t) { }
  }

  static int dp(Context c, float v) { return Math.round(v * c.getResources().getDisplayMetrics().density); }

  static View.OnClickListener close(final Dialog d) {
    return new View.OnClickListener() { public void onClick(View v) { try { d.dismiss(); } catch (Throwable t) { } } };
  }

  static GradientDrawable shape(int[] colors, GradientDrawable.Orientation o, float radius, int strokeW, int stroke) {
    GradientDrawable g = new GradientDrawable(o, colors);
    g.setCornerRadius(radius);
    if (strokeW > 0) g.setStroke(strokeW, stroke);
    return g;
  }

  static TextView text(Context c, CharSequence s, float sp, int color, boolean bold) {
    TextView t = new TextView(c);
    t.setText(s);
    t.setTextSize(TypedValue.COMPLEX_UNIT_SP, sp);
    t.setTextColor(color);
    t.setGravity(Gravity.CENTER);
    t.setTypeface(bold ? Typeface.DEFAULT_BOLD : Typeface.create("sans-serif-medium", Typeface.NORMAL));
    return t;
  }

  static SpannableStringBuilder two(String a, int ca, String b, int cb, String c, int cc) {
    SpannableStringBuilder s = new SpannableStringBuilder(a + b + c);
    s.setSpan(new ForegroundColorSpan(ca), 0, a.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    s.setSpan(new ForegroundColorSpan(cb), a.length(), a.length() + b.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    s.setSpan(new ForegroundColorSpan(cc), a.length() + b.length(), s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    return s;
  }

  static void build(Activity a) {
    if (a.isFinishing() || a.isDestroyed()) return;
    final Dialog d = new Dialog(a);
    d.requestWindowFeature(Window.FEATURE_NO_TITLE);
    int sw = a.getResources().getDisplayMetrics().widthPixels;
    int w = Math.min((int) (sw * 0.92f), dp(a, 400));
    int logo = dp(a, 112);

    FrameLayout root = new FrameLayout(a);
    root.setPadding(dp(a, 12), dp(a, 4), dp(a, 12), dp(a, 12));

    LinearLayout card = new LinearLayout(a);
    card.setOrientation(LinearLayout.VERTICAL);
    card.setGravity(Gravity.CENTER_HORIZONTAL);
    card.setPadding(dp(a, 18), dp(a, 66), dp(a, 18), dp(a, 18));
    card.setBackground(shape(new int[]{0xFFFFFFFF, 0xFFECECF2, 0xFFFFFFFF}, GradientDrawable.Orientation.TL_BR, dp(a, 32), dp(a, 3), RED));
    card.setElevation(dp(a, 14));
    FrameLayout.LayoutParams cl = new FrameLayout.LayoutParams(w, ViewGroup.LayoutParams.WRAP_CONTENT);
    cl.topMargin = logo / 2;
    root.addView(card, cl);

    // Brand pill
    TextView brand = text(a, two("Dark ", DARK, "Gpt", RED, "", RED), 26, DARK, true);
    brand.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD_ITALIC));
    brand.setPadding(dp(a, 24), dp(a, 6), dp(a, 24), dp(a, 6));
    brand.setBackground(shape(new int[]{0xFFFFFFFF, 0xFFF1F1F5}, GradientDrawable.Orientation.TOP_BOTTOM, dp(a, 30), dp(a, 2), 0xFFB9B9C4));
    card.addView(brand);

    TextView head = text(a, two("Welcome to ", DARK, "Dark Gpt", RED, " \uD83D\uDC51", RED), 24, DARK, true);
    LinearLayout.LayoutParams hp = new LinearLayout.LayoutParams(-2, -2);
    hp.topMargin = dp(a, 14);
    card.addView(head, hp);

    LinearLayout sub = new LinearLayout(a);
    sub.setOrientation(LinearLayout.HORIZONTAL);
    sub.setGravity(Gravity.CENTER_VERTICAL);
    View l1 = new View(a), l2 = new View(a);
    l1.setBackgroundColor(0xFFB01020);
    l2.setBackgroundColor(0xFFB01020);
    sub.addView(l1, new LinearLayout.LayoutParams(0, dp(a, 2), 1f));
    TextView st = text(a, "Your Smart AI Assistant", 17, 0xFF2A2A33, false);
    st.setPadding(dp(a, 10), 0, dp(a, 10), 0);
    sub.addView(st);
    sub.addView(l2, new LinearLayout.LayoutParams(0, dp(a, 2), 1f));
    LinearLayout.LayoutParams sp = new LinearLayout.LayoutParams(-1, -2);
    sp.topMargin = dp(a, 8);
    card.addView(sub, sp);

    TextView tag = text(a, two("Ask Anything ", 0xFF2A2A33, "\u2022", RED, " Get Instant Answers ", 0xFF2A2A33), 13.5f, 0xFF2A2A33, false);
    SpannableStringBuilder tg = new SpannableStringBuilder(tag.getText());
    tg.append("\u2022 Explore More");
    tg.setSpan(new ForegroundColorSpan(RED), tg.length() - 15, tg.length() - 14, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    tg.setSpan(new ForegroundColorSpan(0xFF2A2A33), tg.length() - 14, tg.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    tag.setText(tg);
    LinearLayout.LayoutParams tp = new LinearLayout.LayoutParams(-2, -2);
    tp.topMargin = dp(a, 12);
    card.addView(tag, tp);

    LinearLayout info = new LinearLayout(a);
    info.setOrientation(LinearLayout.HORIZONTAL);
    info.setGravity(Gravity.CENTER_VERTICAL);
    info.setPadding(dp(a, 14), dp(a, 10), dp(a, 14), dp(a, 10));
    info.setBackground(shape(new int[]{0xFFFDE8EC, 0xFFFBDDE3}, GradientDrawable.Orientation.TOP_BOTTOM, dp(a, 18), dp(a, 1), 0xFFF2A3B1));
    info.addView(text(a, "\u25C6", 28, RED, true));
    View div = new View(a);
    div.setBackgroundColor(0xFFB0606A);
    LinearLayout.LayoutParams dv = new LinearLayout.LayoutParams(dp(a, 1), dp(a, 34));
    dv.leftMargin = dp(a, 10);
    dv.rightMargin = dp(a, 10);
    info.addView(div, dv);
    TextView pw = text(a, "Powered by Advanced AI Technology\nfor a Smarter Experience", 13, 0xFF2A2A33, false);
    pw.setGravity(Gravity.START);
    info.addView(pw, new LinearLayout.LayoutParams(0, -2, 1f));
    LinearLayout.LayoutParams ip = new LinearLayout.LayoutParams(-1, -2);
    ip.topMargin = dp(a, 14);
    card.addView(info, ip);

    LinearLayout btns = new LinearLayout(a);
    btns.setOrientation(LinearLayout.HORIZONTAL);
    TextView later = text(a, "Later", 17, DARK, true);
    later.setBackground(shape(new int[]{0xFFFFFFFF, 0xFFE3E3EA}, GradientDrawable.Orientation.TOP_BOTTOM, dp(a, 28), dp(a, 2), 0xFFA9A9B8));
    later.setOnClickListener(close(d));
    TextView go = text(a, "Let\u2019s Start  \u2192", 17, Color.WHITE, true);
    go.setBackground(shape(new int[]{0xFFF0222F, 0xFFB00012}, GradientDrawable.Orientation.TOP_BOTTOM, dp(a, 28), dp(a, 2), 0xFF8E000E));
    go.setOnClickListener(close(d));
    LinearLayout.LayoutParams b1 = new LinearLayout.LayoutParams(0, dp(a, 52), 1f);
    b1.rightMargin = dp(a, 6);
    LinearLayout.LayoutParams b2 = new LinearLayout.LayoutParams(0, dp(a, 52), 1f);
    b2.leftMargin = dp(a, 6);
    btns.addView(later, b1);
    btns.addView(go, b2);
    LinearLayout.LayoutParams bp = new LinearLayout.LayoutParams(-1, -2);
    bp.topMargin = dp(a, 16);
    card.addView(btns, bp);

    FrameLayout.LayoutParams lg = new FrameLayout.LayoutParams(logo, logo, Gravity.TOP | Gravity.CENTER_HORIZONTAL);
    root.addView(new LogoView(a), lg);

    TextView x = text(a, "\u2715", 16, Color.WHITE, true);
    GradientDrawable xb = new GradientDrawable();
    xb.setShape(GradientDrawable.OVAL);
    xb.setColor(0xFF2A2A31);
    xb.setStroke(dp(a, 2), 0xFFCFCFD6);
    x.setBackground(xb);
    x.setOnClickListener(close(d));
    FrameLayout.LayoutParams xp = new FrameLayout.LayoutParams(dp(a, 36), dp(a, 36), Gravity.TOP | Gravity.END);
    xp.topMargin = logo / 2 + dp(a, 12);
    xp.rightMargin = dp(a, 12);
    root.addView(x, xp);

    d.setContentView(root, new ViewGroup.LayoutParams(w + dp(a, 24), ViewGroup.LayoutParams.WRAP_CONTENT));
    Window win = d.getWindow();
    if (win != null) {
      win.setBackgroundDrawable(new ColorDrawable(0));
      win.setLayout(w + dp(a, 24), WindowManager.LayoutParams.WRAP_CONTENT);
      win.setDimAmount(0.65f);
      win.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }
    d.show();
  }

  static final class LogoView extends View {
    final Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
    LogoView(Context c) { super(c); }

    @Override protected void onDraw(Canvas c) {
      float w = getWidth(), cx = w / 2, cy = w / 2, r = w / 2 - w * 0.05f;
      p.setStyle(Paint.Style.FILL);
      p.setColor(0xFFFFFFFF);
      c.drawCircle(cx, cy, r, p);
      p.setStyle(Paint.Style.STROKE);
      p.setStrokeWidth(w * 0.05f);
      p.setColor(RED);
      c.drawCircle(cx, cy, r, p);
      p.setStrokeWidth(w * 0.015f);
      p.setColor(0xFFFFB3BA);
      c.drawCircle(cx, cy, r - w * 0.055f, p);
      p.setStrokeWidth(w * 0.042f);
      p.setColor(RED);
      for (int i = 0; i < 6; i++) {
        c.save();
        c.rotate(i * 60, cx, cy);
        c.drawRoundRect(new RectF(cx - w * 0.085f, cy - w * 0.31f, cx + w * 0.085f, cy - w * 0.03f), w * 0.085f, w * 0.085f, p);
        c.restore();
      }
      p.setStyle(Paint.Style.FILL);
      p.setColor(RED);
      c.drawRoundRect(new RectF(w * 0.68f, w * 0.68f, w * 0.97f, w * 0.90f), w * 0.06f, w * 0.06f, p);
      p.setStyle(Paint.Style.STROKE);
      p.setStrokeWidth(w * 0.012f);
      p.setColor(0xFFFFFFFF);
      c.drawRoundRect(new RectF(w * 0.68f, w * 0.68f, w * 0.97f, w * 0.90f), w * 0.06f, w * 0.06f, p);
      p.setStyle(Paint.Style.FILL);
      for (int i = 0; i < 3; i++) c.drawCircle(w * (0.745f + i * 0.075f), w * 0.79f, w * 0.018f, p);
    }
  }
}
