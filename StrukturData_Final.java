package com.mycompany.strukturdata_final;
import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;
import java.util.List;

public class StrukturData_Final {
    public record Penduduk(String Nama, int No_Antrian, Pekerjaan pekerjaan) {}
    public record Pekerjaan(String Pekerjaan) {}

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Queue<Penduduk> peopleInfo = new LinkedList<>();
        List<String> riwayatBansos = new LinkedList<>();

        Pekerjaan ojek = new Pekerjaan("Ojek, ");
        Pekerjaan hansip = new Pekerjaan("Hansip, ");
        Pekerjaan kurir = new Pekerjaan("Kurir, ");
        Pekerjaan guruHonorer = new Pekerjaan("Guru Honorer, ");
        Pekerjaan pedagang = new Pekerjaan("Pedagang, ");

        tambahPenduduk(peopleInfo, "Aceng", 3, ojek);
        tambahPenduduk(peopleInfo, "Osass", 1, hansip);
        tambahPenduduk(peopleInfo, "Fambo", 4, kurir);
        tambahPenduduk(peopleInfo, "Kipli", 5, guruHonorer);
        tambahPenduduk(peopleInfo, "Tarno", 2, pedagang);

        tampilkanNamaPenerimaBansos(peopleInfo);
        tampilkanSemuaPendudukInfo(peopleInfo, scanner, riwayatBansos);
        tampilkanFinalInfo(riwayatBansos);
        tampilkanMenu(peopleInfo, riwayatBansos, scanner);

        scanner.close();
    }

    public static void tambahPenduduk(Queue<Penduduk> peopleInfo, String nama, int noAntrian, Pekerjaan pekerjaan) {
        Penduduk penduduk = new Penduduk(nama, noAntrian, pekerjaan);
        peopleInfo.offer(penduduk);
    }

    public static void tampilkanNamaPenerimaBansos(Queue<Penduduk> peopleInfo) {
        System.out.println("=======================================================");
        System.out.println("              DAFTAR NAMA PENERIMA BANSOS              ");
        System.out.println("=======================================================");
        for (Penduduk person : peopleInfo) {
            System.out.println("Nama: " + person.Nama() + ", Nomor Antrian: " + person.No_Antrian() + ", Pekerjaan: " + person.pekerjaan().Pekerjaan());
        }
        System.out.println("======================================================");
        System.out.println("                   PEMBERIAN BANSOS                   ");
        System.out.println("======================================================");
    }

    public static void tampilkanSemuaPendudukInfo(Queue<Penduduk> peopleInfo, Scanner scanner, List<String> riwayatBansos) {
        int currentAntrian = 1;

        while (currentAntrian <= 5 && !peopleInfo.isEmpty()) {
            Penduduk person = peopleInfo.poll();

            if (person.No_Antrian() == currentAntrian) {
                tambahPendudukInfo(person, scanner, riwayatBansos);
                currentAntrian++;
            } else {
                peopleInfo.offer(person);
            }
        }
    }

    public static void tambahPendudukInfo(Penduduk person, Scanner scanner, List<String> riwayatBansos) {
        System.out.println("Nama: " + person.Nama() + ", Nomor Antrian: " + person.No_Antrian() + ", Pekerjaan: " + person.pekerjaan().Pekerjaan());

        int jarakTempuh = getJarakTempuh(scanner);
        String punyaAnak = getPunyaAnak(scanner);
        StringBuilder PilihOpsi = getPilihBansos(scanner, punyaAnak, jarakTempuh);
        int jumlahBansos = kalkulasiJumlahBansos(jarakTempuh, punyaAnak);

        printBansosPilihan(PilihOpsi);

        riwayatBansos.add("Nama: " + person.Nama() + " Pekerjaan: " + person.pekerjaan().Pekerjaan() + "Jarak Tempuh: " + jarakTempuh + " KM" + ", Punya Anak: " + punyaAnak + ", Bansos: " + getTeksOpsi(PilihOpsi.toString().trim()));
    }

    public static int getJarakTempuh(Scanner scanner) {
        int jarakTempuh = 0;
        boolean inputValid = false;

        while (!inputValid) {
            try {
                System.out.print("Jarak tempuh kerja dalam KILOMETER (tulis dalam bentuk angka): ");
                String input = scanner.next();
                jarakTempuh = Integer.parseInt(input);

                if (jarakTempuh >= 0) {
                    inputValid = true;
                } else {
                    System.out.println("Input harus lebih besar atau sama dengan 0. Silakan coba lagi.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error, input jarak tempuh kerja dalam KILOMETER (tulis dalam bentuk angka). Silakan coba lagi.");
            }
        }

        return jarakTempuh;
    }

    public static String getPunyaAnak(Scanner scanner) {
        String input = "";
        boolean inputValid = false;

        while (!inputValid) {
            System.out.print("Apakah penduduk mempunyai anak? (y/n): ");
            input = scanner.next();

            if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("n")) {
                inputValid = true;
            } else {
                System.out.println("Error, input menggunakan (y/n)");
            }
        }

        return input;
    }

    public static int kalkulasiJumlahBansos(int jarakTempuh, String punyaAnak) {
        int baseBansos = (jarakTempuh > 5) ? 3 : 2;
        int additionalBansos = (punyaAnak.equalsIgnoreCase("y")) ? 1 : 0;
        return baseBansos + additionalBansos;
    }

    public static StringBuilder getPilihBansos(Scanner scanner, String punyaAnak, int jarakTempuh) {
        int jumlahBansos = kalkulasiJumlahBansos(jarakTempuh, punyaAnak);

        System.out.println("");
        System.out.println("List Bansos:");
        System.out.println("1. Mie instan 1 dus");
        System.out.println("2. Beras 10kg");
        System.out.println("3. Minyak 5 liter");
        System.out.println("4. Uang tunai 250 ribu");
        System.out.println("5. Telur 5kg");

        int selectedCount = 0;
        StringBuilder selectedOptions = new StringBuilder();

        while (selectedCount < jumlahBansos) {
            System.out.print("Pilih bansos ke-" + (selectedCount + 1) + " (1-5): ");
            int selectedOption = scanner.nextInt();

            if (selectedOption >= 1 && selectedOption <= 5) {
                if (selectedOptions.indexOf(Integer.toString(selectedOption)) == -1) {
                    selectedOptions.append(selectedOption).append(" ");
                    selectedCount++;
                } else {
                    System.out.println("Anda sudah memilih bansos tersebut. Silakan pilih yang lain.");
                }
            } else {
                System.out.println("Harap memilih nomor yang tersedia (1-5).");
            }
        }
        return selectedOptions;
    }

    public static void printBansosPilihan(StringBuilder selectedOptions) {
        System.out.println("Anda telah memilih bansos: " + getTeksOpsi(selectedOptions.toString().trim()));
        System.out.println();
    }

    public static String getTeksOpsi(String selectedOptions) {
        String[] options = selectedOptions.split(" ");
        StringBuilder selectedOptionsText = new StringBuilder();

        for (String option : options) {
            switch (option) {
                case "1" -> selectedOptionsText.append("Mie instan 1 dus, ");
                case "2" -> selectedOptionsText.append("Beras 10kg, ");
                case "3" -> selectedOptionsText.append("Minyak 5 liter, ");
                case "4" -> selectedOptionsText.append("Uang tunai 250 ribu, ");
                case "5" -> selectedOptionsText.append("Telur 5kg, ");
                default -> {
                }
            }
        }
        return selectedOptionsText.toString().replaceAll(", $", "");
    }

    public static void tampilkanFinalInfo(List<String> riwayatBansos) {
        for (String info : riwayatBansos) {
        }
        System.out.println("=======================================================");
        System.out.println("              INFORMASI RIWAYAT PENDUDUK         ");
        System.out.println("=======================================================");

        tampilkanJumlahPendudukJarakTempuh(riwayatBansos);
        tampilkanJumlahPendudukPunyaAnak(riwayatBansos);
    }

    public static void tampilkanJumlahPendudukJarakTempuh(List<String> riwayatBansos) {
        int jumlahJarakLebih5km = 0;
        int jumlahJarakKurang5km = 0;

        for (String info : riwayatBansos) {
            if (info.contains("Jarak Tempuh: ") && info.contains("Punya Anak:")) {
                int jarakTempuh = Integer.parseInt(info.split("Jarak Tempuh: ")[1].split(" KM")[0]);

                if (jarakTempuh > 5) {
                    jumlahJarakLebih5km++;
                } else {
                    jumlahJarakKurang5km++;
                }
            }
        }

        System.out.println("Jumlah penduduk yang jarak tempuhnya >=5km: " + jumlahJarakLebih5km);
        System.out.println("Jumlah penduduk yang jarak tempuhnya  <5km: " + jumlahJarakKurang5km);
    }

    public static void tampilkanJumlahPendudukPunyaAnak(List<String> riwayatBansos) {
        int jumlahPunyaAnak = 0;
        int jumlahTidakPunyaAnak = 0;

        for (String info : riwayatBansos) {
            if (info.contains("Punya Anak: y")) {
                jumlahPunyaAnak++;
            } else {
                jumlahTidakPunyaAnak++;
            }
        }

        System.out.println("Jumlah penduduk yang memiliki anak: " + jumlahPunyaAnak);
        System.out.println("Jumlah penduduk yang tidak memiliki anak: " + jumlahTidakPunyaAnak);
    }

    public static void tampilkanMenu(Queue<Penduduk> peopleInfo, List<String> riwayatBansos, Scanner scanner) {
        int menuPilihan;
        boolean exitProgram = false;

        do {
            System.out.println("");
            System.out.println("==========================");
            System.out.println("   MENU SEARCH PENERIMA   ");
            System.out.println("==========================");
            System.out.println("1. Search berdasarkan jenis bansos");
            System.out.println("2. End");
            System.out.print("Pilih menu (1-2): ");
            menuPilihan = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (menuPilihan) {
                case 1:
                menuJenisBansos(riwayatBansos, scanner);
                break;
                case 2:
                exitProgram = true;
                System.out.println("Program selesai.");
                break;
                default:
                System.out.println("Pilihan tidak valid.");
                break;
            }
        } while (!exitProgram);
    }
    
    public static void menuJenisBansos(List<String> riwayatBansos, Scanner scanner) {
        System.out.println("================");
        System.out.println("  JENIS BANSOS  ");
        System.out.println("================");
        System.out.println("1. Mie");
        System.out.println("2. Beras");
        System.out.println("3. Minyak");
        System.out.println("4. Uang");
        System.out.println("5. Telur");
        System.out.print("TULIS 1 jenis bansos: ");
        String jenisBansosTampil = scanner.nextLine().trim();
        System.out.println("DATA:");
        System.out.println("-----");

        boolean ditemukan = false;

        for (String bansosInfo : riwayatBansos) {
            if (bansosInfo.contains(jenisBansosTampil)) {
                System.out.println(bansosInfo);
                ditemukan = true;
            }
        }
        

        if (!ditemukan) {
            System.out.println("Data tidak ditemukan.");
        }
    }
}