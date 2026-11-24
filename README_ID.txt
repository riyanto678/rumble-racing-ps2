RUMBLE RACING PS2 - Android Launcher V2

Isi:
- Launcher Android bertema Rumble Racing.
- Pemilih file ISO memakai Storage Access Framework.
- Menyimpan izin baca URI agar file terpilih dapat diakses lagi selama sesi aplikasi.
- Meneruskan ISO ke aplikasi emulator melalui Android ACTION_VIEW chooser.

Batasan integrasi:
Launcher ini TIDAK memuat core emulator ARMSX2. Intent ACTION_VIEW dapat berbeda dukungannya antarversi emulator. Jika ARMSX2 tidak muncul di pemilih aplikasi, buka ARMSX2 langsung dan impor folder/file ISO melalui antarmuka internalnya. Tidak ada package ID atau deep-link ARMSX2 yang diasumsikan di kode ini.

Build APK:
1. Ekstrak ZIP dan buka folder di Android Studio.
2. Gunakan JDK yang direkomendasikan Android Studio dan lakukan Gradle Sync.
3. Build > Build Bundle(s) / APK(s) > Build APK(s).
4. APK debug ada di app/build/outputs/apk/debug/app-debug.apk.

Perlu diketahui:
- Ini bukan emulator PS2 mandiri dan belum dibundel dengan ARMSX2.
- BIOS PS2 dan ISO game tidak disertakan. Gunakan dump BIOS dan backup game yang dibuat sendiri dari perangkat/cakram milik sendiri.
- Target minimum Android 8.0 / API 26. Emulator ARMSX2 membutuhkan perangkat ARM64.
- Performa Rumble Racing di Xiaomi 12 Lite Snapdragon 778G perlu diuji langsung; mulai dengan 1x Native dan Vulkan jika tersedia.

Sumber emulator:
https://github.com/ARMSX2/ARMSX2
