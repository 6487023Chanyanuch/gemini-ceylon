### วิธีการ run Project

1. เปิด cmd และรันคำสั่ง git clone เพื่อ clone project
2. run build เพื่อเปิด project ใน intelliJ
3. จากนั้นไปที่ MySQL เพื่อสร้าง schema ชื่อ backend-ocs
4. เมื่อสร้าง database เสร็จแล้วให้สร้าง user ต่อใน MySQL โดยใช้
   * username: ceylon
   * password: lowsweet
   * localhost
   * และเลือก shecma เป็น backend-ocs
   * เพิ่มสิทธิ์ให้ user เลือก
5. กด run DemoApplication ของ project ใน intelliJ
6. เปิดหน้า website ใช้ path http://localhost:8080/
7. จากนั้น login ด้วย email และ password ดังนี้
   * astronomer
     * email: bai.tul@example.com, password: baiCeylon
     * email: tong.nit@example.com, password: tongCeylon
   * sciobserver
     * email: pim.hah@example.com, password: pimCeylon
     * email: nai.nue@example.com, password: naiCeylon
     * email: my.san@example.com, password: myCeylon
   * user
     * email: fern.wha@example.com, password: fernCeylon
8. ถ้า login ดดยใช้ user ของ astronomer จะเห็นหน้า ที่โชว์ตาราง sciplan ที่ได้ create ไว้และ sciplan ที่ได้รับการ test เสร็จแล้ว ของ ID astronomer คนที่ login
9. astronomer สามารถ create sciplan ได้ดดยกดปุ่ม create sciplan ที่มุมบนด้านซ้ายจากนั้นจะมี pop up ให้กรอกข้อมูลของ sciplan
10. เมื่อกรอกข้อมูลเสร็จแล้วให้กดที่ปุ่ม create sciplan จากนั้นจะเห็น sciplan ที่พึ่งสร้างไว้โชว์อยู่ในตารางที่แสดง sciplan ทั้งหมดที่ astronomer คนนี้สร้างไว้
11. จากนั้นสามารถ test sciplan ที่เราต้องการได้โดยกดปุ่ม test ที่อยู่ใน column สุดท้ายของ sciplan
12. เมื่อกดปุ่ม test จะมี pop up แสดงข้อมูลของ sciplan ที่เราเลอกจะ test จากนั้นเลื่อนไปที่ด้านล่างสุดเลือก test sciplan จากนั้นรอสักครู่ ผลลัพธ์จากการ test แสดงขึ้นว่า sciplan นั้นผ่านการ test หรือไม่
13. เมื่อ test เสร็จ sciplan นั้นจะย้ายไปอยุ่ในตารางของ sciplan ที่ได้รับการ test ที่ด้านล่างของตาราง sciplan และ status ของ sciplan จะเปลี่ยนจาก SAVE เป็น TEATED
14. 








