Система: Windows10 Версия 10.0.19043 Сборка 19043;
Available Processors: 12
Размер heap memory: Первый прогон 256mb, второй 7gb;  

-XX:+UseConcMarkSweepGC
Amount of Young GC: 244, Old GC: 250
All duration (ms) of Young GC: 2602, Old GC: 27149
Average duration (ms) of Young GC: 10, Old GC: 108
Longest duration (ms) of Young GC: 78, Old GC: 175
time(время работы программы до OutOfMemoryError):269
Free memory(остаток памяти на момент OutOfMemoryError): 53
---
Amount of Young GC: 1153, Old GC: 1155
All duration (ms) of Young GC: 65338, Old GC: 1827761
Average duration (ms) of Young GC: 56, Old GC: 1582
Longest duration (ms) of Young GC: 354, Old GC: 2862
time:2152
Free memory: 0

-XX:+UseG1GC
Amount of Young GC: 1469, Old GC: 143
All duration (ms) of Young GC: 10848, Old GC: 8491
Average duration (ms) of Young GC: 7, Old GC: 59
Longest duration (ms) of Young GC: 52, Old GC: 83
time:268
Free memory: 1
---
Amount of Young GC: 6596, Old GC: 744
All duration (ms) of Young GC: 319271, Old GC: 477449
Average duration (ms) of Young GC: 48, Old GC: 641
Longest duration (ms) of Young GC: 206, Old GC: 908
time:1070
Free memory: 35

-XX:+UseParallelGC
Amount of Young GC: 375, Old GC: 226
All duration (ms) of Young GC: 6743, Old GC: 6796
Average duration (ms) of Young GC: 17, Old GC: 30
Longest duration (ms) of Young GC: 43, Old GC: 41
time:205
Free memory: 39
---
Amount of Young GC: 1836, Old GC: 1035
All duration (ms) of Young GC: 242253, Old GC: 436914
Average duration (ms) of Young GC: 131, Old GC: 422
Longest duration (ms) of Young GC: 192, Old GC: 714
time:832
Free memory: 698

-XX:+UseSerialGC
Amount of Young GC: 244, Old GC: 251
All duration (ms) of Young GC: 2886, Old GC: 32222
Average duration (ms) of Young GC: 11, Old GC: 128
Longest duration (ms) of Young GC: 81, Old GC: 183
time:274
Free memory: 52
---
Amount of Young GC: 1153, Old GC: 1158
All duration (ms) of Young GC: 259490, Old GC: 1505921
Average duration (ms) of Young GC: 225, Old GC: 1300
Longest duration (ms) of Young GC: 1295, Old GC: 2250
time:1980
Free memory: 0

-XX:+UnlockExperimentalVMOptions -XX:+UseZGC
Amount of Young GC: 0, Old GC: 91
All duration (ms) of Young GC: 0, Old GC: 4065
Average duration (ms) of Young GC: 0, Old GC: 44
Longest duration (ms) of Young GC: 0, Old GC: 74
time:112
Free memory: 121
---
Amount of Young GC: 0, Old GC: 560
All duration (ms) of Young GC: 0, Old GC: 324501
Average duration (ms) of Young GC: 0, Old GC: 579
Longest duration (ms) of Young GC: 0, Old GC: 17869
time:429
Free memory: 1528

__________________________________________________________________________________________________
Выводы (данные в таблице по heap в 7gb):

| GC              |  Кол-во сборок | All duration(ms)   |Aver duration (ms)|Longest duration (ms)| Time (sec)|     
|-----------------|----------------|--------------------|------------------|---------------------|-----------|     
| ConcMarkSweepGC | Y:1153, O:1155 | 65_338, 1_827_761  |   56,  1_582     |    354, 2862        |   2152    |
| G1GC            | Y:6596, O:744  | 319_271, 477_449   |   48,  641       |    206, 908         |   1070    |
| ParallelGC      | Y:1836, O:1035 | 242_253, 436_914   |   131, 422       |    192, 714         |   832     |
| SerialGC        | Y:1153, O:1158 | 259_490, 1_505_921 |   225, 1_300     |    1_295, 2_250     |   1980    |
| ZGC             | Y:0, O:560     | 0, 324_501         |   0,   579       |    0, 17_869        |   429     |

Безусловно, одна из главных особенностей в работе GC в Java - это "Stop the world" (приложение полностью прекращает свою работу, 
когда идут сборки мусора). Таким образом чем эффективнее идут сборки (для Young Generation и Old Generation разделов памяти), 
тем эффективнее работает вся программа.
Из данных в таблице, можно еще раз убедиться почему G1GC является самым популярным GC: 
Очень быстрые сборки, наименьшее кол-во мажорных сборок, которые гораздо более длительны чем минорные.
Говоря в целом, программа с G1GC мало находилась в "Stop the world" (2 место) и проработала с подтекающий памятью довольно долго (3 место).

Приятное впечатление оставил ParallelGC, меньше всех находился в "Stop the world", имел самую быструю мажорную сборку.
Данные результаты ParallelGC связываю с хорошим количеством доступных потоков процессора и размером памяти. 
Имея хорошие ресурсы на проекте, можно с уверенностью рассмотреть данный GC.

Работу ZGC выношу за скобки анализа, причина: самое короткое время работы программы (повлияло на всю статистику по сборкам), 
а также статус экспериментального GC.
