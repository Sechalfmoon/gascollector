һ����������뻷��
JDK1.8 tomcat8 influxdb 8080�˿ڲ�������Ӧ��ռ�á��������mqttͨѶ���谲װapollo.�������ļ��н���Ӧ�������á�
��������������
1.����Ŀ�ļ�������mvn clean install
2.�ɹ���targetĿ¼�µ�gasmonitor-0.0.1-SNAPSHOT.war�ĳ�gasmonitor.war
3����gasmonitor.war������tomcat webappsĿ¼�£�Ȼ������tomcat
����ϵͳ�ܹ�����
1��ϵͳ��ҪΪȼ���������ϴ������м����ϵͳ����RESTFUL��MQTT����ͨ��Э�顣
2������RESTFULЭ����ǰ��ʹ��post�����ϴ����ݣ��ϴ�ʱ��Header��content-type���ó�application/json,json�������£�
    "hardwareId":"�豸���",
    "temperatur":"����¶�",
    "pressure":"���ѹ��",
   "standard":"�������",
   "running":"��������",
   "summary":"�ܼ�����",
   "pointtime":"���ʱ���"

3������MQTTЭ��ʱʹ��src\main\java\com\gasmonitor\collector\EventMessage.proto�ļ��ṹ����protobuf�������
��
4�����ܲ���RESTFUL����MQTT���������ݺ�д��hazelcast,д���hazelcastΪGasEvent�¼������豸������tenant�š���ΪGasHazelcast�ࡣ
5��ǰ��չ�ֺͺ�̨д��ζ�������hazecast��topic,д�벿�ָ���tenant��д��gasEvent+tenant��measurement.
6��EventController.java����Restful�ϴ����ݣ�Ҳ��ǰ��չ�ֲ����ṩ��ʷ���ݵĲ�ѯ��
����
�ġ�����ע������
1����GasService�е�public void mockHazelcastMap()ע�͵����˴���Ϊ��ģ���豸�����⻧������һ�����Ա���ʵ�������ͬ������������

2����application.propeties�����hazelcast��influxdb����