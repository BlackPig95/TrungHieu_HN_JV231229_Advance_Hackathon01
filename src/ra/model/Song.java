package ra.model;

import java.util.Date;
import java.util.Scanner;

public class Song
{
    public static int songCount = 0;//Dùng để làm việc với array nên để public cho tiện truy cập
    private String songId, songName, description, songWriter;
    private Singer singer;
    private Date createdDate = new Date();
    private boolean songStatus;

    public Song()
    {
    }

    public Song(String songId, String songName, String description, String songWriter, Singer singer, Date createdDate, boolean songStatus)
    {
        this.songId = songId;
        this.songName = songName;
        this.description = description;
        this.songWriter = songWriter;
        this.singer = singer;
        this.createdDate = createdDate;
        this.songStatus = songStatus;
    }

    public String getSongId()
    {
        return songId;
    }

    public void setSongId(String songId)
    {
        this.songId = songId;
    }

    public String getSongName()
    {
        return songName;
    }

    public void setSongName(String songName)
    {
        this.songName = songName;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getSongWriter()
    {
        return songWriter;
    }

    public void setSongWriter(String songWriter)
    {
        this.songWriter = songWriter;
    }

    public Singer getSinger()
    {
        return singer;
    }

    public void setSinger(Singer singer)
    {
        this.singer = singer;
    }

    public Date getCreatedDate()
    {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate)
    {
        this.createdDate = createdDate;
    }

    public boolean isSongStatus()
    {
        return songStatus;
    }

    public void setSongStatus(boolean songStatus)
    {
        this.songStatus = songStatus;
    }

    public void inputData(Scanner scanner, Song[] songs, Singer[] singers)
    {
        while (true)//Song ID
        {
            System.out.println("Nhập mã bài hát, bắt đầu bằng chữ S và có 4 ký tự");
            this.songId = scanner.nextLine();
            if (!songId.startsWith("S"))
            {
                System.out.println("Mã bài hát phải bắt đầu bằng ký tự S (viết hoa)");
            } else if (songId.length() != 4)
            {
                System.out.println("Mã bài hát phải có đúng 4 ký tự");
            } else
            {
                boolean songExisted = false;
                for (int i = 0; i < Song.songCount; i++)
                {
                    if (songs[i].getSongId().equals(songId))
                    {
                        System.out.println("Mã bài hát này đã tồn tại, vui lòng nhập lại");
                        songExisted = true;
                        break;
                    }
                }
                if (!songExisted)
                {
                    break;
                }
            }
        }
        while (true)//Song name
        {
            System.out.println("Nhập tên bài hát");
            this.songName = scanner.nextLine().trim();
            if (songName.isBlank())
            {
                System.out.println("Tên bài hát không được để trống");
            } else break;
        }
        //Song description
        System.out.println("Nhập mô tả về bài hát");
        this.description = scanner.nextLine();

        //Singer
        if (Singer.singerCount > 0)
        {
            System.out.println("Danh sách các ca sĩ đã lưu trong hệ thống");
            for (int i = 0; i < Singer.singerCount; i++)
            {
                singers[i].displayData();
            }
            System.out.println("++++++++++++++++++++++++++");
            System.out.println("Nhập 1 để chọn ca sĩ từ danh sách, nhập 2 để thêm ca sĩ mới");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice)
            {
                case 1:
                    while (true)
                    {
                        System.out.println("Vui lòng nhập mã số của ca sĩ trình bày bài hát");
                        int singerChoice = Integer.parseInt(scanner.nextLine());
                        boolean choiceExist = false;
                        for (int i = 0; i < Singer.singerCount; i++)
                        {
                            if (singerChoice == singers[i].getSingerId())
                            {
                                this.singer = singers[i];
                                choiceExist = true;
                                break;
                            }
                        }
                        if (!choiceExist)
                        {
                            System.out.println("Mã số không tồn tại");
                        } else break;
                    }
                    break;
                case 2:
                    System.out.println("Nhập thông tin của ca sĩ thực hiện bài hát");
                    Singer tempSinger = new Singer();
                    tempSinger.setSingerId(Singer.getSingerMaxId());
                    tempSinger.inputData(scanner);//Hàm inputData sẽ tăng singerCount thêm 1
                    this.singer = tempSinger;
                    singers[Singer.singerCount - 1] = this.singer;//Count lớn hơn index 1 đơn vị => phải -1
                    break;
                default:
                    System.out.println("Lựa chọn không khả dụng");
                    break;
            }
        } else//Nếu singerCount = 0;
        {
            System.out.println("Nhập thông tin của ca sĩ thực hiện bài hát");
            Singer tempSinger = new Singer();
            tempSinger.setSingerId(Singer.getSingerMaxId());
            tempSinger.inputData(scanner);//Hàm inputData sẽ tăng singerCount thêm 1
            this.singer = tempSinger;
            singers[Singer.singerCount - 1] = this.singer;//Count lớn hơn index 1 đơn vị => phải -1
        }

        while (true)//Song Writer
        {
            System.out.println("Nhập thông tin người đã sáng tác ra bài hát");
            this.songWriter = scanner.nextLine().trim();
            if (songWriter.isBlank())
            {
                System.out.println("Không được để trống thông tin người sáng tác");
            } else break;
        }
        //Date created
        this.createdDate = new Date();
        while (true)//Status
        {
            System.out.println("Nhập true hoặc false để xác định trạng thái của bài hát");
            String tempStatus = scanner.nextLine().toLowerCase().trim();
            if (tempStatus.equals("true") || tempStatus.equals("false"))
            {
                break;
            } else System.out.println("Vui lòng nhập đúng true hoặc false");
        }
        Song.songCount++;//Tăng số lượng bài hát khi được gọi
        System.out.println("============================");
    }

    public void displayData()
    {
        System.out.println("Mã bài hát: " + this.songId);
        System.out.println("Tên bài hát: " + this.songName);
        System.out.println("Mô tả bài hát: " + this.description);
        System.out.println("Tên ca sĩ trình bày: " + this.singer.getSingerName());
        System.out.println("Người sáng tác: " + this.songWriter);
        System.out.println("Ngày sáng tác: " + this.createdDate);
        System.out.println("Trạng thái bài hát: " + (this.songStatus ? "Có thể hát" : "Tạm ngừng"));
    }
}
