#create database keep;
use keep;
#alter database keep character set utf8;
drop table if exists `UserOrderRecord`;
drop table if exists `UserExerciseRecord`;
drop table if exists `UserDietRecord`;
drop table if exists `UserPhysicalIndicator`;
drop table if exists `GoodsInfo`;
drop table if exists `SportInfo`;
drop table if exists `FoodInfo`;

#用户注册信息表
#触发器实现，在商品订单插入一条订单记录时，对应的更新当前用户的余额
drop table if exists `UserRegisterInfo`;
create table `UserRegisterInfo`(
  `UserID` int unsigned not null auto_increment primary key , #用户ID
  `UserName` varchar(40) not null , #用户名
  `Password` char(40) not null , #密码
  `Sex` varchar(6) not null , #性别
  `Age` integer not null , #年龄
  `Height` decimal(10,2) not null , #身高（cm）
  `InitialWeight` decimal(10,2) not null ,#初始体重（kg）
  `TargetWeight` decimal(10,2) not null , #目标体重（kg）
  `LoseWeightDuration` integer not null , #计划减重时间（天）
  `CurrentWeight` decimal(10,2),
  `CurrentBMI` decimal(10,2),
  `CurrentRankBMI` varchar(40),
  `CurrentBFR` decimal(10,2),
  `CurrentRankBFR` varchar(40),
  `Balance` decimal(10,2) not null , #账户余额，用户没有充值时为0
  `UserRegisterDay` date not null,  #用户注册日期
  `BMR` decimal(10,2)   #基础代谢（kcal）作为用户每日的推荐摄入热量 早：中：晚 = 3：4:3
                        # 女：BMR = 655 + (9.6 x 体重kg) + (1.8 x 身高cm) - (4.7 x 年龄)
                        # 男：BMR = 66 + (13.7 x 体重kg) + (5 x 身高cm) - (6.8 x 年龄)
)engine = InnoDB default charset = utf8;
#insert into UserRegisterInfo values(null,'龚皓宇','ghy','女',19,164.15,48.73,44.99,10,null,null,null,null,null,0,'2018-06-29',null);
#新用户注册时即UserRegisterInfo插入一条信息时，立即插入一条当天的身体指标
#同时计算其他指标在用户信息表中
drop trigger if exists URI_before_insert_tri;
create trigger URI_before_insert_tri before insert on `UserRegisterInfo`
  for each row
  begin
    if NEW.Sex = 'F' then set NEW.BMR = 655+(9.6*NEW.InitialWeight)+(1.8*NEW.Height)-(4.7*NEW.Age); end if ;
    if NEW.Sex = 'M' then set NEW.BMR = 66+(13.7*NEW.InitialWeight)+(5*NEW.Height)-(6.8*NEW.Age); end if ;
  end;

#食物信息表
#drop table if exists `FoodInfo`;
create table `FoodInfo`(
  `FoodID` char(6) not null primary key , #食物ID
  `FoodName` varchar(100) not null , #食物名称
  `FoodType` varchar(100) not null , #食物种类
  `FoodUnitCalories` decimal(10,2) not null , #食物单位热量 （kcal/100g）
  `Rank` varchar(10)not null , #红灯、绿灯或者黄灯，用来表示该食物是否适合减重期间食用
  `Fat` decimal(10,2) not null , #脂肪含量 （g/100g）
  `Protein` decimal(10,2) not null , #蛋白质含量 （g/100g）
  `Carbohydrates` decimal(10,2) not null  #碳水化合物含量 （g/100g）
)engine = InnoDB default charset = utf8;

#运动项目信息表
#drop table if exists `SportInfo`;
create table `SportInfo`(
  `SportID` varchar(6) not null primary key , #运动项目ID
  `SportName` varchar(40) not null , #运动项目名称
  `SportType` varchar(40) not null , #运动项目类别
  `CaloriesPerSet` decimal(10,2), #消耗热量 （kcal/组）
  `Step` varchar(255) not null  #训练课程，即用文字表述训练步骤
)engine = InnoDB default charset = utf8;

#商品信息表
#drop table if exists `GoodsInfo`;
create table `GoodsInfo`(
  `GoodsID` varchar(6) not null primary key , #商品ID
  `GoodsName` varchar(40) not null , #商品名称
  `GoodsType` varchar(40) not null , #商品分类
  `UnitPrice` decimal(10,2) not null #商品价格
)engine = InnoDB default charset = utf8;

#用户当前身体指标
#触发器实现，新用户注册时即UserRegisterInfo插入一条信息时，立即插入一条当天的身体指标
#drop table if exists `UserPhysicalIndicator`;
create table `UserPhysicalIndicator`(
  `UserID` int unsigned not null ,
  `RecordDay` date not null , #体重记录日期
  `Weight` decimal(10,2) not null , #体重
  `Height` decimal(10,2), #身高
  `BMI` decimal(10,2)  , #根据身高体重求出BMI = 体重kg/(身高m^2)
  `RankBMI` varchar(10)  , #BMI <=18.5 偏轻，18.5<BMI<=24 健康，24<BMI<=28 超重，BMI>28 肥胖
  `BodyFatRatio` decimal(10,2) , #体脂率=1.2×BMI+0.23× 年龄-5.4-10.8×性别（男为1，女为0）
  `RankBFR` varchar(10)  , #体脂率BFR <=21 瘦， 21<BFR<=26 偏瘦, 26<BFR<=31 标准, 31<BFE<=36 微胖, BFR>36 肥胖
  primary key (`UserID`,`RecordDay`),
  foreign key (`UserID`) references UserRegisterInfo(`UserID`)
)engine = InnoDB default charset = utf8;

#当用户插入一条体重记录时UserPhysicalIndicator，数据库自动算出其他各项的值,同时更新当前体重值
drop trigger if exists UPI_before_insert_tri;
create trigger `UPI_before_insert_tri` before insert on `UserPhysicalIndicator`
  for each row
  begin
   declare BMI decimal(10,2);
    declare RankBMI char(10);
    declare BFR decimal(10,2);
    declare RankBFR  char(10);
    declare Age integer;
    declare Sex char(10);
    set Age = (select UserRegisterInfo.Age from `UserRegisterInfo` where UserID = NEW.UserID);
    set Sex = (select UserRegisterInfo.Sex from `UserRegisterInfo` where UserID = NEW.UserID);
    set NEW.Height = (select UserRegisterInfo.Height from `UserRegisterInfo` where UserRegisterInfo.UserID = NEW.UserID);
    set BMI = NEW.Weight / ((NEW.Height/100) * (NEW.Height/100));
    if BMI <=  18.5 then set RankBMI = '偏瘦'; end if;
    if BMI > 18.5 and BMI <= 24 then set  RankBMI = '标准'; end if ;
    if BMI > 24 and BMI <= 28 then  set RankBMI = '超重'; end if ;
    if BMI > 28 then set  RankBMI = '肥胖'; end if ;
    #1.2×BMI+0.23× 年龄-5.4-10.8×性别（男为1，女为0）
    set BFR = 1.2*BMI+0.23*Age-5.4-10.8*(Sex='M');
    if BFR <=  21 then set RankBFR = '瘦'; end if;
    if BFR > 21 and BFR <= 26 then set  RankBFR = '偏廋'; end if ;
    if BFR > 26 and BFR <= 31 then  set RankBFR = '标准'; end if ;
    if BFR > 31 and BFR <= 36 then  set RankBFR = '微胖'; end if ;
    if BFR > 36 then set  RankBFR = '肥胖'; end if ;
    set NEW.BMI = BMI;
    set NEW.RankBMI = RankBMI;
    set NEW.BodyFatRatio  = BFR;
    set NEW.RankBFR = RankBFR;
  end;

drop trigger if exists UPI_after_insert_tri;
create trigger `UPI_after_insert_tri` after insert on `UserPhysicalIndicator`
  for each row
  begin
     update UserRegisterInfo
      set CurrentWeight = (select Weight from UserPhysicalIndicator
                            where UserPhysicalIndicator.UserID = NEW.UserID
                            order by UserPhysicalIndicator.RecordDay desc
                            limit 1)
    where UserRegisterInfo.UserID = NEW.UserID;

    update UserRegisterInfo
      set CurrentBMI = (select UserPhysicalIndicator.BMI from UserPhysicalIndicator
                        where UserPhysicalIndicator.UserID = NEW.UserID
                        order by UserPhysicalIndicator.RecordDay desc limit 1)
    where UserRegisterInfo.UserID = NEW.UserID;

    update UserRegisterInfo
      set CurrentBFR = (select UserPhysicalIndicator.BodyFatRatio from UserPhysicalIndicator
                        where UserPhysicalIndicator.UserID = NEW.UserID
                        order by UserPhysicalIndicator.RecordDay desc limit 1)
    where UserRegisterInfo.UserID = NEW.UserID;

    update UserRegisterInfo
      set CurrentRankBMI = (select UserPhysicalIndicator.RankBMI from UserPhysicalIndicator
                        where UserPhysicalIndicator.UserID = NEW.UserID
                        order by UserPhysicalIndicator.RecordDay desc limit 1)
    where UserRegisterInfo.UserID = NEW.UserID;

    update UserRegisterInfo
      set CurrentRankBFR = (select UserPhysicalIndicator.RankBFR from UserPhysicalIndicator
                        where UserPhysicalIndicator.UserID = NEW.UserID
                        order by UserPhysicalIndicator.RecordDay desc limit 1)
    where UserRegisterInfo.UserID = NEW.UserID;
  end;

#用户饮食记录
#drop table if exists `UserDietRecord`;
create table `UserDietRecord`(
  `UDRID` int unsigned not null primary key auto_increment,
  `UserID` int unsigned not null references UserRegisterInfo(`UserID`),
  `DietRecordDay` date, #记录饮食的日期
  `MealType` varchar(10) not null , #早餐、中餐、晚餐、加餐
  `FoodID` varchar(6) not null references FoodInfo(`FoodID`),
  `FoodName` varchar(40) not null ,
  `FoodCalories` decimal(10,2) not null , #食物单位热量
  `FoodWeight` decimal(10,2) not null,  #食物重量（g）
  `IntakeColories` decimal(10,2) not null #计算该食物的热量kcal
)engine = InnoDB default charset = utf8;


#用户运动记录
#drop table if exists `UserExerciseRecord`;
create table `UserExerciseRecord`(
  `UERID` int unsigned not null primary key auto_increment,
  `UserID` int unsigned not null references UserRegisterInfo(`UserID`),
  `ExerciseRecordDay` date, #记录进行该项运动的日期
  `SportID` varchar(6) not null references SportInfo(`SportID`),
  `SportName` varchar(40) not null ,
  `SportType` varchar(40) not null ,
  `TrainSet` integer, #运动组数 min
  `BurnCalories` decimal(10,2) not null #计算该项运动消耗的热量kcal
)engine = InnoDB default charset = utf8;

#用户订单记录
#drop table if exists `UserOrderRecord`;
create table `UserOrderRecord`(
  `UORID` int unsigned not null primary key auto_increment,
  `UserID` int unsigned not null references UserRegisterInfo(`UserID`),
  `OrderRecordDay` date, #记录这笔订单的日期
  `GoodsID` char(6) not null references GoodsInfo(`GoodsID`),
  `GoodsName` varchar(100) not null ,
  `UnitPrice` decimal(10,2) not null , #商品单价
  `GoodsQuantity` integer not null , #商品数目
  `TotalCost` decimal(10,2) not null, #商品总价
  `Address` varchar(255) not null , #收货地址
  `Telephone` varchar(100) not null  #联系电话
)engine = InnoDB default charset = utf8;

#触发器实现，在商品订单插入一条订单记录时，对应的更新当前用户的余额
drop trigger if exists UOR_after_insert_tri;
create trigger `UOR_after_insert_tri` after insert on `UserOrderRecord`
  for each row
  begin
    update `UserRegisterInfo`
      set Balance = Balance - NEW.TotalCost
    where UserID = NEW.UserID;
  end;

#insert into UserRegisterInfo
#values(null,'龚皓宇','ghy','女',19,164.15,48.73,44.99,10,'2018-06-29',0);
#insert into UserDietRecord values(null,1,'2018-07-02','午餐','100001','玉米',112.0,200.11055410204835,224.12382059429416);