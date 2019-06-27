# GAPANA - Tanggap Bencana

Aplikasi tanggap bencana yang bertujuan untuk mengedukasi orang tentang apa yang harus dilakukan ketika bencana terjadi, memberikan trauma healing berupa video-video pilihan yang bersumber dari youtube, menginformasikan posko evakuasi bencana (**dummy data**), memberi informasi cuaca di kota-kota sekitar, memberikan berita bencana terkini, serta memberi notifikasi ketika terjadi bencana*

*Untuk dapat melihat notifikasi, perlu menjalankan web backend terlebih dahulu, karena service yang kami gunakan tidak mendukung cron job gratis.

# Struktur File

Proyek ini terdiri dari 2 folder utama, yakni **Gapana** dan **Web**.
Folder **Gapana** berisi proyek dan aplikasi Gapana itu sendiri.
Folder **Web** berisi proyek website backend yang kami gunakan untuk scrap berita, scrap bencana terkini dari website BMKG, serta untuk mengirim notifikasi ke aplikasi Gapana.

## Menginstall Aplikasi Gapana pada Smartphone

Untuk menjalankan aplikasi ini, pastikan anda memiliki koneksi internet yang baik, karena sebagian besar fitur ini membutuhkan internet untuk mengaksesnya.

 1. Clone project ini
 2. Buka folder Gapana > app > build > outputs > apk > debug > **Gapana.apk**
 3. Copy file tersebut ke smartphone anda
 4. Pastikan smartphone anda memiliki sistem operasi Kitkat (4.4) keatas. Kami merekomendasikan menggunakan OS Lollipop (5.0) keatas
 5. Install hingga selesai
 6. Kemudian aplikasi siap digunakan

## Mengirim Notifikasi Bencana

Pastikan anda memiliki **Node.js** terinstall pada laptop/pc anda

1. Clone project ini
2. Buka folder web > bmkg-notif > functions
3. Kemudian buka terminal/cmd pada folder tersebut
4. Jalankan `yarn start`
5. Kode akan mengeksekusi fungsi yang bertugas untuk mengambil data dari bmkg dan apabila diketahui ada bencana, maka akan mengirimkan notifikasi pada aplikasi Gapana
6. Oleh karena tidak selalu terjadi bencana, untuk mencoba fitur notifikasi, silahkan edit kode pada file **index.js** baris ke 36
7. Ubah fungsi `scrap()` menjadi `sendFCMTest()`
8. Kode tersebut akan mengirimkan notifikasi dummy ke aplikasi Gapana
