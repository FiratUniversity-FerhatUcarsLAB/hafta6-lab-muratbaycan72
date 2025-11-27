import java.util.Scanner;

/**
 * Ad Soyad: Murat Baycan
 * Öğrenci No: 240541022
 * Proje: Proje 1 - Öğrenci Not Değerlendirme Sistemi
 * Tarih: 27/11/2025
 */

public class Proje1_NotSistemi {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("--- Öğrenci Not Değerlendirme Sistemi ---");

        System.out.print("Vize Notu (0-100): ");
        double vize = scan.nextDouble();

        System.out.print("Final Notu (0-100): ");
        double finalNot = scan.nextDouble();

        System.out.print("Ödev Notu (0-100): ");
        double odev = scan.nextDouble();

        // Hesaplamalar
        double ortalama = calculateAverage(vize, finalNot, odev);
        boolean gectiMi = isPassingGrade(ortalama);
        char harfNotu = getLetterGrade(ortalama);
        boolean onurListesi = isHonorList(ortalama, vize, finalNot, odev);
        boolean butunlemeHakki = hasRetakeRight(ortalama);

        // Çıktı Formatı
        System.out.println("\n--- SONUÇ RAPORU ---");
        System.out.printf("Ortalama: %.2f\n", ortalama);
        System.out.println("Harf Notu: " + harfNotu);
        
        if (gectiMi) {
            System.out.println("Durum: GEÇTİ");
            if (onurListesi) {
                System.out.println("Başarı: ONUR LİSTESİNE GİRDİNİZ!");
            }
        } else {
            System.out.println("Durum: KALDI");
            if (butunlemeHakki) {
                System.out.println("Uyarı: BÜTÜNLEME HAKKINIZ VAR.");
            } else {
                System.out.println("Uyarı: Bütünleme hakkınız yok, dersi tekrar almalısınız.");
            }
        }
    }

    // Ortalama Hesaplama: Vize %30 + Final %40 + Ödev %30
    public static double calculateAverage(double vize, double finalNot, double odev) {
        return (vize * 0.30) + (finalNot * 0.40) + (odev * 0.30);
    }

    // Geçme Kontrolü (Ortalama >= 50)
    public static boolean isPassingGrade(double ortalama) {
        return ortalama >= 50;
    }

    // Harf Notu Belirleme
    public static char getLetterGrade(double ortalama) {
        if (ortalama >= 90) return 'A';
        else if (ortalama >= 80) return 'B';
        else if (ortalama >= 70) return 'C';
        else if (ortalama >= 60) return 'D';
        else if (ortalama >= 50) return 'E';
        else return 'F';
    }

    // Onur Listesi: Ortalama >= 85 VE Tüm notlar >= 70
    public static boolean isHonorList(double ortalama, double v, double f, double o) {
        return (ortalama >= 85) && (v >= 70) && (f >= 70) && (o >= 70);
    }

    // Bütünleme Hakkı: 40 <= Ortalama < 50
    public static boolean hasRetakeRight(double ortalama) {
        return (ortalama >= 40) && (ortalama < 50);
    }
}
