# Hướng Dẫn Các Tính Năng Mới Của SpawnerMeta

Tài liệu này hướng dẫn về hai tính năng mới vừa được triển khai trong SpawnerMeta: **Tự Động Gộp Khi Đặt (Auto-Merge on Place)** và **Phá Lẻ Từng Spawner (Break Single from Stack)**.

---

## 🏗️ 1. Tự Động Gộp Khi Đặt (Auto-Merge on Place)

Tính năng này cho phép người chơi tự động gộp một spawner mới đặt vào một spawner đã có sẵn cùng loại trong một bán kính nhất định. Điều này loại bỏ việc phải gộp thủ công (Shift + Chuột Phải) sau khi đặt.

### Cách thức hoạt động
- Khi người chơi đặt một spawner, plugin sẽ tìm kiếm spawner đang stack cùng loại gần nhất trong bán kính đã cấu hình.
- Nếu tìm thấy mục tiêu hợp lệ, spawner vừa đặt sẽ được "hấp thụ" vào stack hiện có, tăng số lượng của nó lên.
- Một dải hạt (particle) sẽ hiển thị từ vị trí đặt đến spawner mục tiêu để thông báo việc gộp thành công.

### Cấu hình
Trong file `configuration.yml`, bạn có thể tìm thấy các thiết lập sau:

```yaml
Modifiers:
  stacking:
    merge-on-place:
      # Bật hoặc tắt tính năng tự động gộp
      enabled: true
      # Bán kính tìm kiếm mặc định (1-16 khối)
      radius: 3
      # Bán kính cụ thể cho từng loại entity (tùy chọn)
      radius-per-type:
        COW: 5
        ZOMBIE: 1
```

### Yêu cầu
- Tính năng stacking phải được bật toàn cục (`Modifiers.stacking.enabled: true`).
- Người chơi phải có quyền `spawnermeta.stacking` (hoặc cấu hình bỏ qua quyền).
- Các spawner phải cùng loại và có các nâng cấp/số lần dùng (charges) giống hệt nhau (khớp hoàn toàn).

---

## ⛏️ 2. Phá Lẻ Từng Spawner (Break Single from Stack)

Tính năng này thay đổi hành vi khi phá các spawner đang stack. Thay vì phá toàn bộ stack và rơi ra tất cả spawner, người chơi có thể chọn chỉ phá từng spawner một ra khỏi stack.

### Cách thức hoạt động
- Khi người chơi phá một spawner đang stack bằng công cụ có Silk Touch, chỉ **một** spawner được gỡ khỏi khối và rơi ra/trao cho người chơi.
- Phần stack còn lại vẫn giữ nguyên vị trí trong thế giới.
- Nếu số lượng stack chỉ còn 1, khối sẽ bị phá bình thường.
- **Mẹo**: Để phá **toàn bộ stack** cùng lúc ngay cả khi tính năng này đang bật, bạn chỉ cần giữ **Shift + Chuột Trái** (ngồi xổm khi phá).

### Cấu hình
Trong file `configuration.yml`, bạn có thể tìm thấy các thiết lập sau trong phần breaking:

```yaml
Modifiers:
  breaking:
    silk-requirement:
      # Nếu bật, chỉ một spawner bị gỡ khỏi stack mỗi lần phá
      break-single: true
```

### Yêu cầu
- Công cụ phải có Silk Touch (hoặc có quyền `spawnermeta.breaking.bypass.silktouch`).
- Tính năng này chỉ kích hoạt nếu `break-single` được đặt thành `true` trong cấu hình.

---

## 📂 Tổng hợp các đường dẫn cấu hình
- `Modifiers.stacking.merge-on-place.enabled`
- `Modifiers.stacking.merge-on-place.radius`
- `Modifiers.stacking.merge-on-place.radius-per-type.<LOẠI>`
---

## 🔡 3. Placeholder Hỗ Trợ Chữ Hoa (Uppercase Placeholders)

Tính năng này cho phép bạn sử dụng phiên bản chữ hoa của các placeholder thông thường trong các file ngôn ngữ hoặc cấu hình.

### Cách thức hoạt động
- Bất kỳ placeholder nào (ví dụ: `%name%`, `%type%`, `%player%`) đều có thể được chuyển sang chữ hoa bằng cách thêm hậu tố `_uppercase`.
- Ví dụ: `%name_uppercase%` sẽ chuyển "Pig" thành "PIG".
- Các thẻ định dạng màu sắc (như `<#FFFFFF>`) và định dạng văn bản (như `!bold`) vẫn sẽ hoạt động chính xác ngay cả khi nằm trong chuỗi được chuyển sang chữ hoa.

### Ví dụ sử dụng
Trong `language.yml`:
```yaml
Holograms:
  regular:
    single: "<#bfffff-#00ffff>%name_uppercase% Spawner"
```
Kết quả hiển thị: **PIG Spawner** (thay vì Pig Spawner).

### Các placeholder hỗ trợ:
Mọi placeholder được plugin đăng ký đều tự động hỗ trợ phiên bản `_uppercase`, bao gồm nhưng không giới hạn ở:
- `%name_uppercase%`
- `%type_uppercase%`
- `%player_uppercase%`
- `%world_uppercase%`
- `%material_uppercase%`
