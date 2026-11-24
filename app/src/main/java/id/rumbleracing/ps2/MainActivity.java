package id.rumbleracing.ps2;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import android.graphics.Color;
import android.graphics.Typeface;

public class MainActivity extends Activity {
    private static final int PICK_ISO = 41;
    private Uri gameUri;
    private TextView selected, status;

    private int dp(float v) { return (int)(v * getResources().getDisplayMetrics().density + 0.5f); }

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setStatusBarColor(Color.rgb(12,12,16));
        getWindow().setNavigationBarColor(Color.rgb(12,12,16));

        LinearLayout page = new LinearLayout(this);
        page.setOrientation(LinearLayout.VERTICAL);
        page.setPadding(dp(22), dp(26), dp(22), dp(20));
        page.setBackgroundColor(Color.rgb(12,12,16));

        TextView eyebrow = new TextView(this);
        eyebrow.setText("PLAYSTATION 2 • ANDROID");
        eyebrow.setTextColor(Color.rgb(255,75,65));
        eyebrow.setTextSize(11);
        eyebrow.setGravity(Gravity.CENTER);
        page.addView(eyebrow);

        TextView logo = new TextView(this);
        logo.setText("RUMBLE\\nRACING");
        logo.setTextColor(Color.WHITE);
        logo.setTextSize(38);
        logo.setGravity(Gravity.CENTER);
        logo.setTypeface(Typeface.create("sans-serif-black", Typeface.BOLD));
        LinearLayout.LayoutParams logoLp = new LinearLayout.LayoutParams(-1, dp(145));
        logoLp.setMargins(0, dp(8), 0, dp(8));
        page.addView(logo, logoLp);

        TextView tagline = new TextView(this);
        tagline.setText("THE RACE STARTS HERE");
        tagline.setTextColor(Color.LTGRAY);
        tagline.setTextSize(12);
        tagline.setGravity(Gravity.CENTER);
        page.addView(tagline);

        View divider = new View(this);
        divider.setBackgroundColor(Color.rgb(205,40,40));
        LinearLayout.LayoutParams divLp = new LinearLayout.LayoutParams(-1, dp(2));
        divLp.setMargins(0, dp(22), 0, dp(18));
        page.addView(divider, divLp);

        TextView label = new TextView(this);
        label.setText("GAME FILE");
        label.setTextColor(Color.rgb(255,90,80));
        label.setTextSize(11);
        label.setTypeface(null, Typeface.BOLD);
        page.addView(label);

        selected = new TextView(this);
        selected.setText("Belum ada file ISO dipilih");
        selected.setTextColor(Color.WHITE);
        selected.setTextSize(14);
        selected.setPadding(dp(12), dp(12), dp(12), dp(12));
        selected.setBackgroundColor(Color.rgb(29,29,36));
        LinearLayout.LayoutParams selLp = new LinearLayout.LayoutParams(-1, -2);
        selLp.setMargins(0, dp(8), 0, dp(14));
        page.addView(selected, selLp);

        Button choose = button("PILIH FILE ISO");
        page.addView(choose, fullButton());
        choose.setOnClickListener(v -> chooseIso());

        Button play = button("BUKA DI EMULATOR PS2");
        LinearLayout.LayoutParams playLp = fullButton();
        playLp.setMargins(0, dp(12), 0, 0);
        page.addView(play, playLp);
        play.setOnClickListener(v -> openInEmulator());

        status = new TextView(this);
        status.setText("Status: emulator belum diperiksa.");
        status.setTextColor(Color.rgb(255,190,100));
        status.setTextSize(12);
        LinearLayout.LayoutParams statusLp = new LinearLayout.LayoutParams(-1, -2);
        statusLp.setMargins(0, dp(14), 0, 0);
        page.addView(status, statusLp);

        TextView help = new TextView(this);
        help.setText("\\nPERSIAPAN\\n1. Instal ARMSX2 dari halaman resmi GitHub.\\n2. Jalankan ARMSX2 satu kali, lalu impor BIOS hasil dump dari konsol PS2 milik sendiri.\\n3. Pilih file backup Rumble Racing berformat ISO di sini.\\n4. Tekan BUKA DI EMULATOR. Jika ARMSX2 tidak muncul, buka ARMSX2 dan tambahkan lokasi ISO dari menu game di dalam emulator.\\n\\nCatatan: aplikasi ini launcher khusus, bukan mesin emulasi. BIOS dan ISO tidak disertakan.");
        help.setTextColor(Color.rgb(185,185,195));
        help.setTextSize(12);
        help.setLineSpacing(dp(3), 1f);
        LinearLayout.LayoutParams helpLp = new LinearLayout.LayoutParams(-1, -2);
        helpLp.setMargins(0, dp(12), 0, 0);
        page.addView(help, helpLp);

        TextView footer = new TextView(this);
        footer.setText("RUMBLE RACING PS2 • V2");
        footer.setTextColor(Color.DKGRAY);
        footer.setTextSize(10);
        footer.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams footLp = new LinearLayout.LayoutParams(-1, 0, 1);
        page.addView(footer, footLp);
        setContentView(page);
    }

    private Button button(String text) {
        Button b = new Button(this);
        b.setText(text);
        b.setTextColor(Color.WHITE);
        b.setAllCaps(false);
        b.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.rgb(185,35,42)));
        return b;
    }

    private LinearLayout.LayoutParams fullButton() {
        return new LinearLayout.LayoutParams(-1, dp(52));
    }

    private void chooseIso() {
        Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("*/*");
        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        startActivityForResult(i, PICK_ISO);
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_ISO && resultCode == RESULT_OK && data != null) {
            gameUri = data.getData();
            try {
                getContentResolver().takePersistableUriPermission(gameUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } catch (Exception ignored) {}
            String name = gameUri.getLastPathSegment();
            selected.setText(name == null ? gameUri.toString() : name);
            status.setText("Status: ISO dipilih. Siap diteruskan ke emulator.");
        }
    }

    private void openInEmulator() {
        if (gameUri == null) {
            Toast.makeText(this, "Pilih ISO Rumble Racing terlebih dahulu.", Toast.LENGTH_LONG).show();
            return;
        }
        Intent view = new Intent(Intent.ACTION_VIEW);
        view.setDataAndType(gameUri, "application/octet-stream");
        view.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            startActivity(Intent.createChooser(view, "Pilih emulator PS2"));
            status.setText("Status: memilih aplikasi untuk membuka ISO.");
        } catch (Exception e) {
            status.setText("Tidak ada aplikasi yang menerima file. Buka ARMSX2 dan impor ISO dari dalam emulator.");
        }
    }
}
