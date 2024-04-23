# วิธีการ run Project

1. เปิด cmd และใช้คำสั่ง `git clone` เพื่อ clone project ลงบนเครื่อง
2. เปิดโปรเจคใน IntelliJ IDEA และรัน build
3. ทำการสร้าง schema ใน MySQL ชื่อ `backend-ocs`
4. สร้างผู้ใช้ใน MySQL ด้วยข้อมูลต่อไปนี้:
   - Username: ceylon
   - Password: lowsweet
   - Host: localhost
   - Schema: backend-ocs
   - กำหนดสิทธิ์ให้ผู้ใช้สามารถเข้าถึง schema นี้ได้ (Select All)
5. รัน `DemoApplication` ใน IntelliJ IDEA
6. เปิดเว็บไซต์ที่ http://localhost:8080/
7. เข้าสู่ระบบโดยใช้อีเมลและรหัสผ่านตามข้อมูลด้านล่าง:

   **Astronomer:**
   - Email: bai.tul@example.com, Password: baiCeylon
   - Email: tong.nit@example.com, Password: tongCeylon

   **SciObserver:**
   - Email: pim.hah@example.com, Password: pimCeylon
   - Email: nai.nue@example.com, Password: naiCeylon
   - Email: my.san@example.com, Password: myCeylon

# ขั้นตอนสร้างและทดสอบ SciPlan

1. เมื่อเข้าสู่ระบบด้วยบัญชีของ Astronomer คุณจะเห็นตาราง SciPlan ที่มี SciPlan ที่ถูกสร้างและทดสอบไว้แล้วของผู้ใช้ที่เข้าสู่ระบบ 
2. สามารถสร้าง SciPlan โดยกดปุ่ม `Create SciPlan` และกรอกข้อมูลที่ต้องการในหน้าต่างที่ pop-up ขึ้นมา
3. เมื่อกรอกข้อมูลเสร็จสิ้น ให้กดปุ่ม `Create` และตามด้วย `OK` เมื่อแจ้งเตือนแสดงขึ้นว่า Sciplan ได้ถูกสร้างเวร็จแล้ว
4. SciPlan ที่สร้างจะแสดงในตาราง SciPlan ที่ผู้ใช้ได้สร้างไว้
5. คุณสามารถทดสอบ SciPlan โดยกดปุ่ม `Test` ที่อยู่ในคอลัมน์สุดท้ายของแต่ละ SciPlan
6. เมื่อกด test SciPlan แล้วให้รอสักครู่ ผลลัพธ์จากการ test แสดงขึ้นว่า SciPlan นั้นผ่านการ test หรือไม่
7. เมื่อทดสอบเสร็จสิ้น สถานะของ SciPlan จะเปลี่ยนจาก `SAVED` เป็น `TESTED` และ SciPlan จะถูกย้ายไปยังตาราง SciPlan ที่ผ่านการทดสอบ

# ขั้นตอนสร้าง Observing Program

1. เมื่อเข้าสู่ระบบด้วยบัญชีของ SciObserver คุณจะเห็นตาราง SciPlan ที่ถูกสร้างไว้และตรงส่วนด้านล่างของตารางจะมี Observing Program ที่เป็นช่องว่างๆไว้ (ต้องสร้าง Observing Program ก่อนถึงจะเห็น)
2. SciObserver สามารถสร้าง Observing Program ได้โดยกดปุ่มที่อยู่ในคอลัมน์สุดท้ายของแต่ละ SciPlan
3. หลังจากกดปุ่ม `Create Observing Program` คุณจะได้แสดงหน้าต่างที่ pop-up เพื่อให้กรอกข้อมูล Observing Program
4. เมื่อกรอกข้อมูลเสร็จสิ้น ให้กดปุ่ม `Submit` และตามด้วย `OK` เมื่อแจ้งเตือนแสดงขึ้น
5. Observing Program ที่สร้างจะแสดงในช่องว่างด้านล่างตรงส่วนของ Observing Program


# Design Patterns 







