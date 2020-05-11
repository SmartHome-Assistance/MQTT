# Smart Home Assistance

![](https://upload.wikimedia.org/wikipedia/commons/3/34/Android_Studio_icon.svg) 
![](https://img.shields.io/github/release/pandao/editor.md.svg) 



# Описание
Приложение преднозначено для простого и удобного взаимодействия пользователе с системой умного дома. Для опросов состояний датчика, а также их изменения и вывов статистики использования.

# Использованные технологии
-  Firebase \- для регистрации и аунтификация пользователей
- Paho client MQTT \- для общения клиента с сервером
- SQL \- для зранения данных длиента


# Жизнь приложения 
## Аctivity
1. MainActivity - главное активити для отображения фрагментов со вкладок 
2. LoginActivity - для авторизации пользователя
3. RegistrationActivity - для регистрации новго пользователя
3. SettingActivity - для изменения настроек приложения (уведомления, обновления топиков)
4. PresentationScreen - экран приветсвования пользователя

## Service
1. MqttMessageService - сервис для отображения уведомлений с топиков

## Fragment
1. DashboardFragment - для отображения важных стостояний сервера и топиков, а также их изменения (такие как свет, громкость, пол и тп)
2. ManageFragment - настройка подключения и клиент сервиса (подписка, отписка  топика и публикация текста на топик)
3. EventFragment - автоматические сценарии для изменения состояний датчиков
4. StatisticFragment - составления статистики по использованию устройств



### Скриншоты

![](https://github.com/SmartHome-Assistance/MQTT/blob/master/scr/1.jpg) ![](https://github.com/SmartHome-Assistance/MQTT/blob/master/scr/2.jpg)

> Authorization and registration.

![](https://github.com/SmartHome-Assistance/MQTT/blob/master/scr/8.jpg) ![](https://github.com/SmartHome-Assistance/MQTT/blob/master/scr/3.jpg) ![](https://github.com/SmartHome-Assistance/MQTT/blob/master/scr/4.jpg) ![](https://github.com/SmartHome-Assistance/MQTT/blob/master/scr/5.jpg) ![](https://github.com/SmartHome-Assistance/MQTT/blob/master/scr/6.jpg)

> Main Activity with fragments

![](https://github.com/SmartHome-Assistance/MQTT/blob/master/scr/7.jpg)

> Settings Activity


### End