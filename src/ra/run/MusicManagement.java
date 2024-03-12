package ra.run;

import ra.model.Singer;
import ra.model.Song;

import java.util.Scanner;

public class MusicManagement
{

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        //Vì dùng static array không thay đổi độ lớn được nên phải tạo mảng 100 phần tử rỗng để CRUD
        Singer[] singerList = new Singer[100];
        Song[] songList = new Song[100];

        while (true)
        {
            System.out.println("*************MUSIC-MANAGEMENT**************");
            System.out.println("1. Quản lý ca sĩ");
            System.out.println("2. Quản lý bài hát");
            System.out.println("3. Tìm kiếm bài hát");
            System.out.println("4. Thoát");
            System.out.println("Nhập thao tác muốn lựa chọn theo các số từ 1-4");
            int menuChoice = Integer.parseInt(scanner.nextLine());
            switch (menuChoice)
            {
                case 1:
                    singerManagement(scanner, songList, singerList);
                    break;
                case 2:
                    songManagement(scanner, songList, singerList);
                    break;
                case 3:
                    searchManagement(scanner, songList, singerList);
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Lựa chọn không khả dụng");
                    break;
            }
        }
    }

    public static void singerManagement(Scanner scanner, Song[] songList, Singer[] singerList)
    {
        while (true)
        {
            System.out.println("*************SINGER-MANAGEMENT**************");
            System.out.println("1. Nhập thông tin của các ca sĩ mới");
            System.out.println("2. Hiển thị danh sách các ca sĩ đã có");
            System.out.println("3. Cập nhật thông tin ca sĩ theo mã ca sĩ");
            System.out.println("4. Xóa ca sĩ theo mã ca sĩ");
            System.out.println("5. Quay lại");
            System.out.println("Nhập thao tác muốn lựa chọn theo các số từ 1-5");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice)
            {
                case 1:
                    System.out.println("Nhập số lượng ca sĩ muốn thêm mới");
                    int n = Integer.parseInt(scanner.nextLine());
                    for (int i = 0; i < n; i++)
                    {//Số lượng ca sĩ sẽ được tăng khi hàm inputData được gọi, Id cũng sẽ tăng theo
                        Singer newSinger = new Singer();
                        newSinger.setSingerId(Singer.getSingerMaxId());//Đặt mã Id theo maxId
                        newSinger.inputData(scanner);
                        singerList[Singer.singerCount - 1] = newSinger;//Count lớn hơn index 1 đơn vị => phải -1
                        System.out.println("------------------------");
                    }
                    break;
                case 2:
                    if (Singer.singerCount == 0)
                    {
                        System.out.println("Không có ca sĩ nào để hiển thị");
                        break;
                    }
                    for (int i = 0; i < Singer.singerCount; i++)
                    {
                        System.out.println("Thông tin của ca sĩ thứ " + (i + 1));
                        singerList[i].displayData();
                    }
                    break;
                case 3:
                    if (Singer.singerCount == 0)
                    {
                        System.out.println("Không có ca sĩ nào để cập nhật");
                        break;
                    }
                    Singer updateSinger = findSingerId(scanner, singerList);
                    if (updateSinger != null)
                    {
                        System.out.println("Mời nhập thông tin mới cho ca sĩ");
                        updateSinger.inputData(scanner);//Hàm inputData sẽ tăng singerCount và maxId thêm 1
                        Singer.singerCount--;//Giảm số lượng đi vì không có ca sĩ mới
                        Singer.setSingerMaxId(Singer.getSingerMaxId() - 1);//Giảm maxId đi 1 vì không có ca sĩ mới
                        System.out.println("Thông tin đã cập nhật của ca sĩ: ");
                        updateSinger.displayData();
                    } else
                    {
                        System.out.println("Không có ca sĩ nào khớp với lựa chọn");
                    }
                    break;
                case 4:
                    if (Singer.singerCount == 0)
                    {
                        System.out.println("Không có ca sĩ nào để xóa");
                        break;
                    }
                    System.out.println("Nhập mã ca sĩ muốn xóa");
                    int id = Integer.parseInt(scanner.nextLine());
                    boolean hasSong = false;
                    for (int i = 0; i < Song.songCount; i++)
                    {
                        if (id == songList[i].getSinger().getSingerId())
                        {//Nếu mã ca sĩ trùng với mã nhập vào => Có bài hát
                            hasSong = true;
                            System.out.println("Ca sĩ này có bài hát liên kết, không được xóa");
                            break;
                        }
                    }
                    if (!hasSong)//Không có bài hát thì mới cho xóa
                    {
                        boolean singerExist = false;
                        for (int i = 0; i < Singer.singerCount; i++)
                        {
                            if (id == singerList[i].getSingerId())
                            {
                                singerList[i] = null;
                                singerExist = true;
                                for (int j = i; j < Singer.singerCount - 1; j++)
                                {
                                    singerList[j] = singerList[j + 1];//Shift elements to the left
                                }
                                singerList[Singer.singerCount] = null;
                                Singer.singerCount--;
                                break;
                            }
                        }
                        if (!singerExist)
                            System.out.println("Không có ca sĩ nào khớp với mã này");
                        else break;
                    }
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Lựa chọn không khả dụng");
                    break;
            }
        }
    }

    public static void songManagement(Scanner scanner, Song[] songList, Singer[] singerList)
    {
        while (true)
        {
            System.out.println("*************SONG-MANAGEMENT**************");
            System.out.println("1. Thêm vào các bài hát");
            System.out.println("2. Hiển thị danh sách các bài hát đã lưu trữ");
            System.out.println("3. Cập nhật thông tin bài hát theo mã bài hát");
            System.out.println("4. Xóa bài hát theo mã số bài hát");
            System.out.println("5. Quay lại");
            System.out.println("Nhập thao tác muốn lựa chọn theo các số từ 1-5");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice)
            {
                case 1:
                    System.out.println("Nhập số lượng bài hát muốn thêm mới");
                    int n = Integer.parseInt(scanner.nextLine());
                    for (int i = 0; i < n; i++)
                    {
                        Song newSong = new Song();
                        //Hàm inputData sẽ tự động tăng songCount thêm 1
                        newSong.inputData(scanner, songList, singerList);
                        songList[Song.songCount - 1] = newSong;//Count lớn hơn index 1 đơn vị => phải -1
                        System.out.println("------------------------");
                    }
                    break;
                case 2:
                    if (Song.songCount == 0)
                    {
                        System.out.println("Hiện không có bài hát nào để hiển thị");
                        break;
                    }
                    for (int i = 0; i < Song.songCount; i++)
                    {
                        songList[i].displayData();
                    }
                    break;
                case 3:
                    if (Song.songCount == 0)
                    {
                        System.out.println("Hiện không có bài hát nào để cập nhật");
                        break;
                    }
                    System.out.println("Danh sách các bài hát");
                    for (int i = 0; i < Song.songCount; i++)
                    {
                        System.out.println("Tên bài hát: " + songList[i].getSongName()
                                + " Mã số bài hát: " + songList[i].getSongId());
                    }
                    System.out.println("Mời nhập mã bài hát muốn cập nhật");
                    String updateSong = scanner.nextLine();
                    boolean songFound = false;
                    for (int i = 0; i < Song.songCount; i++)
                    {
                        if (updateSong.equals(songList[i].getSongId()))
                        {
                            System.out.println("Mời nhập thông tin mới cho bài hát");
                            songList[i].inputData(scanner, songList, singerList);
                            Song.songCount--;//Giảm số lượng đi 1 vì không có bài hát mới
                            System.out.println("Thông tin mới của bài hát: ");
                            songList[i].displayData();
                            songFound = true;
                            break;
                        }
                    }
                    if (!songFound)
                        System.out.println("Không tìm thấy bài hát để cập nhật");
                    else break;
                    break;
                case 4:
                    if (Song.songCount == 0)
                    {
                        System.out.println("Hiện không có bài hát nào để xóa");
                        break;
                    }
                    System.out.println("Danh sách các bài hát");
                    for (int i = 0; i < Song.songCount; i++)
                    {
                        System.out.println("Tên bài hát: " + songList[i].getSongName()
                                + " Mã số bài hát: " + songList[i].getSongId());
                    }
                    System.out.println("Mời nhập mã bài hát muốn xóa");
                    String deleteSong = scanner.nextLine();
                    boolean songExist = false;
                    for (int i = 0; i < Song.songCount; i++)
                    {
                        if (deleteSong.equals(songList[i].getSongId()))
                        {
                            songList[i] = null;
                            songExist = true;
                            for (int j = i; j < Song.songCount - 1; j++)
                            {
                                songList[j] = songList[j + 1];//Shift elements to the left
                            }
                            songList[Song.songCount] = null;
                            Song.songCount--;
                            break;
                        }
                    }
                    if (!songExist)
                        System.out.println("Lựa chọn không hợp lệ");
                    else break;
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Lựa chọn không khả dụng");
                    break;
            }
        }
    }

    public static void searchManagement(Scanner scanner, Song[] songList, Singer[] singerList)
    {
        while (true)
        {
            System.out.println("*************SEARCH-MANAGEMENT**************");
            System.out.println("1. Tìm kiếm bài hát theo tên ca sĩ hoặc tên thể loại");
            System.out.println("2. Tìm kiếm ca sĩ theo tên hoặc thể loại");
            System.out.println("3. Hiển thị danh sách bài hát theo thứ tự tên tăng dần");
            System.out.println("4. Hiển thị 10 bài hát được đăng mới nhất");
            System.out.println("5. Quay lại");
            System.out.println("Nhập thao tác muốn lựa chọn theo các số từ 1-5");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice)
            {
                case 1:
                    if (Song.songCount == 0)
                    {
                        System.out.println("Không có bài hát để tìm kiếm");
                        break;
                    }
                    System.out.println("Nhập tên hoặc thể loại của ca sĩ để tìm các bài hát");
                    String searchName = scanner.nextLine().trim();
                    boolean songFound = false;
                    for (int i = 0; i < Song.songCount; i++)
                    {
                        if (songList[i].getSinger().getSingerName().contains(searchName))
                        {
                            System.out.print("Tìm thấy bài hát được thể hiện bởi ca sĩ này: ");
                            System.out.println(songList[i].getSongName());
                            songFound = true;
                        } else if (songList[i].getSinger().getGenre().contains(searchName))
                        {
                            System.out.print("Tìm thấy bài hát có ca sĩ hát thể looại này thể hiện: ");
                            System.out.println(songList[i].getSongName());
                            songFound = true;
                        }
                    }
                    if (!songFound)
                        System.out.println("Không tìm thấy bài hát nào khớp với yêu cầu");
                    break;
                case 2:
                    if (Singer.singerCount == 0)
                    {
                        System.out.println("Không có ca sĩ nào để tìm kiếm");
                        break;
                    }
                    System.out.println("Nhập tên hoặc thể loại của ca sĩ mà bạn muốn tìm");
                    String singerSearchName = scanner.nextLine();
                    boolean singerFound = false;
                    for (int i = 0; i < Singer.singerCount; i++)
                    {
                        if (singerList[i].getSingerName().contains(singerSearchName))
                        {
                            System.out.print("Tìm thấy ca sĩ có tên giống với mô tả: ");
                            System.out.println(singerList[i].getSingerName());
                            singerFound = true;
                        } else if (singerList[i].getGenre().contains(singerSearchName))
                        {
                            System.out.print("Tìm thấy ca sĩ có thể loại giống với mô tả: ");
                            System.out.println(singerList[i].getSingerName());
                            singerFound = true;
                        }
                    }
                    if (!singerFound)
                        System.out.println("Không tìm thấy ca sĩ khớp với mô tả");
                    break;
                case 3:
                    if (Song.songCount == 0)
                    {
                        System.out.println("Hiện không có bài hát nào để hiển thị");
                        break;
                    }
                    for (int i = 0; i < Song.songCount; i++)
                    {
                        for (int j = 0; j < Song.songCount - 1; j++)
                        {
                            String tempName1 = songList[j].getSongName();
                            String tempName2 = songList[j + 1].getSongName();
                            int result = tempName1.compareToIgnoreCase(tempName2);
                            if (result > 0)//=>tempName1 > tempName2
                            {
                                Song tempSong = songList[j];
                                songList[j] = songList[j + 1];
                                songList[j + 1] = tempSong;
                            }
                        }
                    }
                    for (int i = 0; i < Song.songCount; i++)
                    {
                        System.out.println("Bài hát: " + songList[i].getSongName());
                    }
                    break;
                case 4:
                    if (Song.songCount == 0)
                    {
                        System.out.println("Hiện không có bài hát nào để hiển thị");
                        break;
                    }
                    for (int i = 0; i < Song.songCount; i++)
                    {
                        for (int j = 0; j < Song.songCount - 1; j++)
                        {
                            long tempTime1 = songList[j].getCreatedDate().getTime();
                            long tempTime2 = songList[j + 1].getCreatedDate().getTime();
                            if (tempTime1 < tempTime2)
                            {//Giá trị getTime càng lớn thì nghĩa là thời điểm tạo càng gần => shift về trái
                                Song tempSong = songList[j];
                                songList[j] = songList[j + 1];
                                songList[j + 1] = tempSong;
                            }
                        }
                    }
                    int tenRecent = Math.min(Song.songCount, 10);//Nếu songCount < 10 => trả về songCount
                    for (int i = 0; i < tenRecent; i++)//Tránh bị null pointer
                    {
                        System.out.println((i + 1) + " Bài hát: " + songList[i].getSongName());
                    }
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Lựa chọn không khả dụng");
                    break;
            }
        }
    }

    public static Singer findSingerId(Scanner scanner, Singer[] singerList)
    {
        System.out.println("Nhập mã ca sĩ muốn tìm kiếm");
        int id = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < Singer.singerCount; i++)
        {
            if (id == singerList[i].getSingerId())
            {
                return singerList[i];
            }
        }
        return null;
    }
}
