import java.util.Scanner;

/**
 * Ad Soyad: Murat Baycan
 * Öğrenci No: 240541022
 * Proje: Proje 2 - Sinema Bileti Fiyatlandırma
 * Tarih: 27/11/2025
 */

public class Proje2_SinemaBileti {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("--- SİNEMA BİLETİ SİSTEMİ ---");

        System.out.println("Gün Seçiniz (1-Pzt ... 7-Paz): ");
        int gun = scan.nextInt();

        System.out.print("Saat (0-23): ");
        int saat = scan.nextInt();

        System.out.print("Yaş: ");
        int yas = scan.nextInt();

        System.out.println("Meslek (1:Öğrenci, 2:Öğretmen, 3:Diğer): ");
        int meslek = scan.nextInt();

        System.out.println("Film Türü (1:2D, 2:3D, 3:IMAX, 4:4DX): ");
        int filmTuru = scan.nextInt();

        // Ana Metot Çağrısı
        calculateFinalPrice(gun, saat, yas, meslek, filmTuru);
    }

    // Hafta sonu kontrolü (Cumartesi(6) veya Pazar(7))
    public static boolean isWeekend(int gun) {
        return (gun == 6 || gun == 7);
    }

    // Matine kontrolü (Saat 12'den önce)
    public static boolean isMatinee(int saat) {
        return saat < 12;
    }

    // Temel Fiyat Hesaplama
    public static double calculateBasePrice(int gun, int saat) {
        boolean haftaSonu = isWeekend(gun);
        boolean matine = isMatinee(saat);

        if (haftaSonu) {
            return matine ? 55.0 : 85.0; // Hafta sonu Matine / Normal
        } else {
            return matine ? 45.0 : 65.0; // Hafta içi Matine / Normal
        }
    }

    // Format Ekstra Ücreti (Switch-Case)
    public static double getFormatExtra(int filmTuru) {
        switch (filmTuru) {
            case 2: return 25.0; // 3D
            case 3: return 35.0; // IMAX
            case 4: return 50.0; // 4DX
            default: return 0.0; // 2D veya geçersiz
        }
    }

    // İndirim Hesaplama
    public static double calculateDiscount(int yas, int meslek, int gun) {
        double indirimOrani = 0.0;

        // Yaş İndirimleri
        if (yas > 65) {
            indirimOrani = 0.30;
        } else if (yas < 12) {
            indirimOrani = 0.25;
        } 
        // Meslek İndirimleri
        else {
            switch (meslek) {
                case 1: // Öğrenci
                    if (gun >= 1 && gun <= 4) { // Pzt-Prş
                        indirimOrani = 0.20;
                    } else if (gun >= 5 && gun <= 7) { // Cum-Paz
                        indirimOrani = 0.15;
                    }
                    break;
                case 2: // Öğretmen
                    if (gun == 3) { // Sadece Çarşamba
                        indirimOrani = 0.35;
                    }
                    break;
            }
        }
        return indirimOrani;
    }

    // Fiyat Hesaplama ve Yazdırma Koordinasyonu
    public static double calculateFinalPrice(int gun, int saat, int yas, int meslek, int filmTuru) {
        double temelFiyat = calculateBasePrice(gun, saat);
        double indirimOrani = calculateDiscount(yas, meslek, gun);
        double ekstraUcret = getFormatExtra(filmTuru);
        
        double indirimTutari = temelFiyat * indirimOrani;
        double araToplam = temelFiyat - indirimTutari;
        double sonFiyat = araToplam + ekstraUcret;

        generateTicketInfo(temelFiyat, indirimTutari, ekstraUcret, sonFiyat);
        return sonFiyat;
    }

    // Bilgi Yazdırma
    public static void generateTicketInfo(double temel, double indirim, double ekstra, double toplam) {
        System.out.println("\n--- BİLET DETAYI ---");
        System.out.printf("Temel Fiyat: %.2f TL\n", temel);
        System.out.printf("İndirim Tutarı: -%.2f TL\n", indirim);
        System.out.printf("Format Ekstra: +%.2f TL\n", ekstra);
        System.out.println("-------------------------");
        System.out.printf("TOPLAM TUTAR: %.2f TL\n", toplam);
    }
}
