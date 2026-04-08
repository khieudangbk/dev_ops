# dev_ops: Microservices Observability Demo

Dự án này là một ví dụ minh họa về kiến trúc Microservices sử dụng **Spring Boot**, kết hợp với một hệ thống theo dõi và giám sát (observability stack) toàn diện từ Grafana. Nó bao gồm 2 dịch vụ (services) chính và các thành phần phân tích, giám sát như Prometheus, Grafana, Loki (thông qua Promtail), và Tempo.

## 🚀 Cấu trúc hệ thống

### 1. Ứng dụng Microservices
- **Order Service**: Dịch vụ quản lý đơn hàng. Chạy ở port `8080`. Dịch vụ này sẽ gọi đến Inventory Service qua WebClient.
- **Inventory Service**: Dịch vụ quản lý kho. Chạy ở port `8081`.

### 2. Observability Stack (Công cụ giám sát & phân tích)
- **Grafana** (`3000`): Giao diện tổng hợp để trực quan hóa dữ liệu logs, metrics và traces.
- **Prometheus** (`9090`): Thu thập và lưu trữ các chỉ số (metrics) của hệ thống.
- **Loki** (`3100`): Thu thập và quản lý logs.
- **Promtail**: Agent chuyển tiếp logs của Docker containers đến Loki.
- **Tempo** (`3200`): Distributed Tracing để theo dõi luồng request (traces) xuyên suốt các microservices, nhận dữ liệu qua giao thức OpenTelemetry (`4317`).

## 🛠️ Hướng dẫn cài đặt và chạy ứng dụng

### Yêu cầu tiên quyết
- [Docker](https://docs.docker.com/get-docker/) và Docker Compose đã được cài đặt.
- [Java 17](https://jdk.java.net/17/) (hoặc cao hơn) và Maven (nếu muốn build thủ công không qua Docker).

### Các bước chạy hệ thống

1. Build và khởi động tất cả các container bằng Docker Compose (được cấu hình sẵn trong `docker-compose.yml`):
   ```bash
   docker-compose build
   docker-compose up -d
   ```

2. Kiểm tra xem các container đã chạy thành công chưa:
   ```bash
   docker-compose ps
   ```

## 🌐 Các dịch vụ và cổng (Ports)

| Tên Service       | Port nội bộ / Port máy chủ | Truy cập/URL | Chức năng chính |
|-------------------|-----------------------------|-------------|-----------------|
| Order Service     | 8080 : 8080                 | `http://localhost:8080` | Quản lý đơn hàng. Đầu vào API. |
| Inventory Service | 8080 : 8081                 | `http://localhost:8081` | Quản lý kho. Nhận request từ Order. |
| Grafana           | 3000 : 3000                 | `http://localhost:3000` | Dashboard giám sát (login mặc định `admin/admin`). |
| Prometheus        | 9090 : 9090                 | `http://localhost:9090` | Thu thập metrics tổng thể. |
| Loki              | 3100 : 3100                 | `http://localhost:3100` | Database lưu trữ Logs. |
| Tempo             | 3200 : 3200                 | `http://localhost:3200` | Tracing. |

## 📖 Cách hoạt động và kiểm tra (Testing)

1. Gửi request đến `Order Service` để tạo đơn hàng. Order Service sẽ tự động tiến hành gọi qua `Inventory Service` bằng WebClient (hoàn thành một distributed flow).
2. Khi request được thực thi thành công, Spring Boot (thông qua Micrometer/OpenTelemetry) sẽ ghi nhận các thông số tương ứng và đẩy về phía Tempo, Prometheus.
3. Đồng thời, Log của container sẽ được hệ thống docker thu thập thông qua `Promtail` đẩy thẳng về `Loki`.
4. Mở **Grafana** (`http://localhost:3000`), sau đó cấu hình các Data Source:
    - **Prometheus**: URL `http://prometheus:9090`
    - **Loki**: URL `http://loki:3100`
    - **Tempo**: URL `http://tempo:3200`
5. Tìm kiếm logs và quan sát Trace ID giữa các dịch vụ trong Grafana để nghiệm chứng sự liên lạc giữa *Order Service* và *Inventory Service*.

---
*Dự án phục vụ mục đích học hỏi, thử nghiệm kỹ năng Dev Ops / Observability và quản trị microservices.*