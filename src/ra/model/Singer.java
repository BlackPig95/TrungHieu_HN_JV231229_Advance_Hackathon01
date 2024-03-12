package ra.model;

import java.util.Scanner;

public class Singer
{
    private static int singerMaxId = 1;//Kiểm soát id của ca sĩ
    public static int singerCount = 0;//Dùng để làm việc với array nên để public cho tiện truy cập
    private int singerId, age;
    private String singerName, nationality, genre;
    private boolean gender;

    public Singer()
    {
    }

    public Singer(int singerId, int age, String singerName, String nationality, String genre, boolean gender)
    {
        this.singerId = singerId;
        this.age = age;
        this.singerName = singerName;
        this.nationality = nationality;
        this.genre = genre;
        this.gender = gender;
    }

    public static int getSingerMaxId()
    {
        return singerMaxId;
    }

    public static void setSingerMaxId(int singerMaxId)
    {
        Singer.singerMaxId = singerMaxId;
    }

    public int getSingerId()
    {
        return singerId;
    }

    public void setSingerId(int singerId)
    {
        this.singerId = singerId;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public String getSingerName()
    {
        return singerName;
    }

    public void setSingerName(String singerName)
    {
        this.singerName = singerName;
    }

    public String getNationality()
    {
        return nationality;
    }

    public void setNationality(String nationality)
    {
        this.nationality = nationality;
    }

    public String getGenre()
    {
        return genre;
    }

    public void setGenre(String genre)
    {
        this.genre = genre;
    }

    public boolean isGender()
    {
        return gender;
    }

    public void setGender(boolean gender)
    {
        this.gender = gender;
    }

    public void inputData(Scanner scanner)
    {
        while (true)//Singer Name
        {
            System.out.println("Nhập tên ca sĩ");
            this.singerName = scanner.nextLine().trim();
            if (singerName.isBlank())
            {
                System.out.println("Không được để trống tên ca sĩ");
            } else break;
        }
        while (true)//Singer age
        {
            System.out.println("Nhập tuổi của ca sĩ");
            this.age = Integer.parseInt(scanner.nextLine());
            if (age <= 0)
            {
                System.out.println("Tuổi ca sĩ phải lớn hơn 0");
            } else break;
        }
        while (true)//Singer Nationality
        {
            System.out.println("Nhập quốc tịch của ca sĩ");
            this.nationality = scanner.nextLine().trim();
            if (nationality.isBlank())
            {
                System.out.println("Không được để trống quốc tịch");
            } else break;
        }
        while (true)//Gender
        {
            System.out.println("Nhập true hoặc false để xác định giới tính ca sĩ (true= nam, false = nữ)");
            String tempGender = scanner.nextLine().toLowerCase().trim();
            if (tempGender.equals("true") || tempGender.equals("false"))
            {
                break;
            } else System.out.println("Vui lòng nhập đúng true hoặc false");
        }
        while (true)//Genre
        {
            System.out.println("Nhập dòng nhạc mà ca sĩ này hát");
            this.genre = scanner.nextLine().trim();
            if (genre.isBlank())
            {
                System.out.println("Không được để trống dòng nhạc");
            } else break;
        }
        Singer.singerCount++;//Tăng số lượng ca sĩ khi được gọi
        singerMaxId++;//Tăng Id của singer mỗi khi có người mới
    }

    public void displayData()
    {
        System.out.println("Mã ca sĩ: " + this.singerId);
        System.out.println("Tên ca sĩ: " + this.singerName);
        System.out.println("Tuổi của ca sĩ: " + this.age);
        System.out.println("Quốc tịch: " + this.nationality);
        System.out.println("Giới tính: " + (this.gender ? "Nam" : "Nữ"));
        System.out.println("Dòng nhạc: " + this.genre);
    }
}
