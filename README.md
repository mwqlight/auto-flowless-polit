# Auto-Flowless-Polit
Auto Flowless Polit 是一个智能化全自动的流程驾驶舱，将会覆盖流程的所有业务场景和需要，并且将流程智能化，实现自动化，AI化的智能体。

## 项目结构
```
auto-flowless-1/
├── auto-flowless-backend/   # 后端项目
├── auto-flowless-frontend/  # 前端项目
├── start-backend.sh         # 后端启动脚本
├── start-frontend.sh        # 前端启动脚本
└── README.md                # 项目说明文档
```

## 快速开始
### 环境要求
- Node.js 20.10.0+ (前端)
- Java 8+ (后端)
- MySQL 5.7+ (数据库)
- Redis 5.0+ (缓存)

### 启动步骤
1. 初始化数据库
   ```bash
   # 执行后端项目中的sql文件
   mysql -u root -p auto_flowless < auto-flowless-backend/sql/xxx.sql
   ```

2. 启动后端服务
   ```bash
   chmod +x start-backend.sh
   ./start-backend.sh
   ```

3. 启动前端服务
   ```bash
   chmod +x start-frontend.sh
   ./start-frontend.sh
   ```

## 开发说明
项目采用分阶段开发模式，每个阶段的开发要求和任务请参考对应的具体的文档来开发。

## 许可证
MIT

