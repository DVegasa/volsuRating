# ВолГУ Рейтинг
![Showcase](https://i.ibb.co/vJBp4Jp/Group-1-small.png)

## Итог разработки

>ВолГУ Рейтинг для android! 
>
>Это приложение поможет тебе ответить на вопрос: “Это у всех так мало баллов или только у меня? Мне нужно переживать?” Ты получишь удобную статистику и графики по каждому предмету, а также оценку своей позиции, она отображается смайликом возле каждого предмета. 
>
>Буду признателен, если ты распространишь это приложение среди своих знакомых, может оно им понравится =)
>
>Вот ссылка для скачивания на Android: https://drive.google.com/open?id=1vDIOVVMUbiIThmju3-0GPbWqGW-kqJZy	



С таким текстом я отправил свой билд знакомым и знакомым знакомых.

## Контекст разработки

Я учусь в Волгоградском Государственном Университете (ВолГУ). Тут действует балльно-рейтинговая система, где твоя оценка определяется количеством баллов на конец семестра. Сейчас существует две системы, чтобы узнать свои баллы (в дальнейшем слово «рейтинг» будет эквивалентно слову «баллы») – через сайт ВолГУ и бота ВКонтакте. Сайт предлагает пятиэтапную форму авторизации (её потом можно будет не проходить, сохранив ссылку на результаты) и в итоге даёт сводную табличку баллов всех студентов по всем предметам за конкретный семестр.

![](https://i.ibb.co/tQJ2fVT/image-20200103195912036.png)
*Рейтинг студентов моей группы, полученный с сайта ВолГУ*

**Плюсы**:

* Видно баллы всех студентов в группе, поэтому можешь проанализировать свою ситуацию (из релизной заметки: *“Это у всех так мало баллов или только у меня? Мне нужно переживать?”*)
* Знаешь свою позицию в группе по сумме баллов

**Минусы**:

* Нужно помнить номер своей зачётки
* Неудобно смотреть смотреть свои баллы (особенно с телефона)

Поняв минусы этой системы, разработчик из ВолГУ решил написать бота для ВК, который по номеру твоей зачётки даёт исключительно твои баллы

> Данные были обновлены: 26.12.19 11:00
> Ваша группа: МОСб-192
> У тебя есть к чему стремиться, ты на 12 из 25 месте 😏
>
> Разница с человеком выше: 2 бал.
>
> Русский язык и культура речи Зач.: 66 бал.
>
> Физическая культура и спорт Зач.: 16 бал.
>
> Прикладная физическая культура (ОФП) Зач.: 0 бал.
>
> Алгебра и теория чисел Зач.с.Оц.: 42 бал.
>
> Геометрия и топология Зач.с.Оц.: 87 бал.
>
> Иностранный язык Зач.с.Оц.: 43 бал.
>
> Математический анализ Зач.с.Оц.: 24 бал.
>
> Информатика и программирование Экз.: 60 бал.
>
> Прикладная физическая культура (Волейбол) : 0 бал.
>
> Прикладная физическая культура (Адаптивная физическая культура) : 0 бал.
>

**Плюсы**:

* Показывает только твои баллы
* Присылает уведомление об изменении баллов

**Минусы**:

* Нельзя узнать баллы других для сравнения
* Нельзя узнать свою позицию по отдельному предмету

## Почему я решил создать своё приложение?
В ходе учёбы я мог пропускать пары (как по уважительным причинам, так и нет 😉), но я хотел закончить семестр с хорошими баллами, поэтому мониторил баллы остальных в группе, поскольку думал «Блин, у меня 24 балла по физкультуре. Уже почти конец семестра, когда я успею набрать баллы?!», а потом смотрел через сайт на баллы других людей и видел, что такая ситуация у всех, значит преподаватель так ставит баллы и для него это нормально. Так я делал около месяца, а потом подумал: «Почему бы не сделать мобильное приложение для этой цели?..». И сделал

## Поставленные задачи
* Создать приложение, отображающее твой рейтинг по каждому предмету и статистику по баллам в группе, для оценки ситуации
* Упростить анализ своей ситуации – не считать свою позицию вручную, а прибегнуть к статистическим подсчётам на устройстве и отображению human-friendly результата

## Дизайн
Каждую свою разработку я начинаю с того, что делаю дизайн своего текущего проекта. Зачем я это делаю и почему считаю это полезным делом я напишу в одной из следующих статей. 

Дизайн-макеты, представленные в этом блоке, созданы в Figma лично мной

![](https://i.ibb.co/cDzyQHy/image-20200103202003129.png)

При создании дизайна я ориентировался на брендбук университета. Брендбук есть, но исходники своих графических элементов, кроме логотипа, они не выложили, что послужило для меня проблемой. Однако, позже я решил не использовать эти элементы и взял из брендбука только цветовую палитру и основной шрифт приложения.

## Разработка

### Этап первый – получение данных

Чтобы анализировать данные, их надо сначала получить. Я считал, что раз есть чат-бот в ВК, то где-то есть API для получения этих данных, или же какой-нибудь лёгкий способ. Ну не парсить же HTML страничку с результатом (спойлер – да, парсить). 

Я нашёл разработчика этого чат-бота и написал ему. Как оказалось, это сотрудник ВолГУ и он имеет прямой доступ к БД, где эти баллы хранятся. В частности, из-за этого бот умеет отправлять уведомления об изменении баллов. По итогу нашего общения, я не получил API и принял решение писать парсер HTML. Но для начала, этот HTML нужно было получить.

>  volsu.ru/rating/?plan_id=**АРМ003689**&zach=All&semestr=**1**&group=**МОСб-192**

Это ссылка, по которой можно получить страничку с нужной тебе табличкой. В ходе исследования я понял, что все поля поддаются закономерному объяснению кроме `plan_id`, его значение бралось непонятным образом по остальной информации. Чтобы получить эту ссылку на самом сайте, как я уже упоминал была пятифакторная авторизация:

1. Выбор института
2. Выбор направления
3. Выбор группы
4. Выбор семестра
5. Выбор своей зачётки

После получения всех пяти данных сервер возвращал эту ссылку. Я не мог построить эту ссылку вручную, поскольку не знал алгоритм генерации поля `plan_id`. Дополнительно, по номеру зачётки я не мог узнать номер группы (поле `group`). Я решил, что в моём приложении вход будет осуществляться через этот сайт ВолГУ.

Через WebView я встроил этот сайт себе в приложении и просил пользователя авторизоваться через эту форму. Каждую секунду я проверял ссылку на совпадение нужной мне (используя RegEx) и если она совпадала, предлагал пользователю войти под этими данными. Саму ссылку я сохранял и получал из неё данные на будущее, чтобы не заставлять пользователя повторять эту процедуру каждый вход.

Теперь я мог скачивать HTML и парсить его. Сам парсер я писал ещё до реализации авторизации, поскольку я мог скачать HTML страничку заранее через обычный браузер, проанализировать её и получить нужные данные, что я и сделал. Полученный класс я импортировал в свой андроид-проект и вуа-ля! Оно заработало. Я получал данные о рейтинге всех студентов, с привязкой к их номеру зачётки, у меня был номер зачётки текущего пользователя и я мог начать делать магию!

### Этап второй – обработка

Для user-friendly интерфейса отображения статистики я выбрал смайлики. Они вписываются в дизайн-макет, информативные, достаточно яркие и легко создают ассоциативность, используя цвет и форму. 

Для подсчёта состояния дел я решил прибегнуть к статистике. Но, так как данных было мало и их важность была не критичной, я не стал изобретать какой-то мега-способ анализа, а просто воспользовался подсчётом терцилей. То есть, отсортировал все баллы по предмету по возрастанию и взял три равные части, с примерно одинаковым кол-вом элементов в каждом

![](https://i.ibb.co/j8m3pgw/image-20200103210407672.png)

Если студент оказывался в нижнем терциле (где баллы ниже всего), то это означало его плохую позицию и опасность – нужно набирать баллы. Если в средней, то это отображало нормальный ход событий. Если в верхней, то хорошие успехи в этом предмете. 

Отдельно были выделены те предметы, балл по которому перешагнул порог максимальной оценки (например, 60 для предметов с формой сдачи «Зачёт» и 91 для «Экзамен») независимо от рейтинга остальных получали смайлик ✔, говорящий о том, что выше стремиться уже некуда и пользователь в безопасности. Также карточка каждого предмета содержала значение о медианном балле и рейтинге пользователя в этом предмете

![](https://i.ibb.co/X862sVR/image-20200103210919765.png)
*Карточка предмета*

График показывает кол-во студентов, которые имеют соответствующий балл (чем правее на оси OX, тем больше балл). Высота первого столбика (самый левый, которого нет на скриншоте) показывает количество студентов с баллами от 0 до 4. Второй – от 5 до 9. Третий от 10 до 14. И так далее. Всего таких столбиков двадцать.

### Этап третий – отображение результата

Тут проблем не было. Разве что, я думал, что мне придётся самому писать View для отображения графика, поскольку думал, что он достаточно сложный и нет готовой библиотеки. Однако я ошибся – библиотека была и она удовлетворяла мои нужды. Я решил не усложнять себе жизнь и использовать её. Результат меня устраивает.

### Этап четвёртый – публикация и получение статистики

Это был для меня новый этап. Все мои прошлые приложения откладывались «в стол» и не видели свет. Тут же, я решил поделиться своим результатом работы со всеми студентами.

Мне было интересно узнать, сколько людей скачает моё приложение и будет им пользоваться. Я решил подключить к приложению модуль аналитики. Мне нужна была простая аналитика – узнать кол-во людей и из какой группы они (интересно знать свою аудиторию). Простой не нашёл. Подключил Firebase analytics. Инструмент кажется мне сейчас сложным, неудобным для моих целей, но кол-во людей узнать я могу. Стоит также отметить, что релиз я сделал в не лучшее время – конец семестра, все свои баллы уже знают и как-то нет необходимости узнавать свои баллы. Но всё же. Релиз состоялся 30 декабря. Сегодня 3 января. Распространял приложение я через ВК рассылку друзьям, с просьбой переслать в общий чат их группы. Конечно, это не Google Play, но для начала этого хватит.

![](https://i.ibb.co/vwsKdsh/image-20200103211946213.png)
*Статистика моего приложения*

## Что дальше?

Это приложение я перестану развивать, однако я начинаю разработку другого, более глобального приложения, где одной из возможностей будет как раз этот рейтинг. По факту, я просто встрою это приложение в другое.

## Выводы, полученные знания и польза этого проекта

* Довёл приложение с нуля до релиза 
* Научился работать с `git tag` и узнал про то, как выдаются версии проектам
* Работал с WebView, парсингом HTML страницы и сопутствующими технологиями
* Твои друзья, которым ты скидываешь бета-версию приложения для поиска багов, скорее всего, не будут искать там баги и ты не получишь от них фидбек. Бета-версию я отправил 5 людям и получил отзывы только от двух.
* Получил опыт в написании статьи про разработку приложения (эта статья и дала опыт)

## Заключение

Исходный код проекта доступен на моём GitHub – https://github.com/DVegasa/volsuRating

Ссылка для скачивания APK файла – https://drive.google.com/open?id=1vDIOVVMUbiIThmju3-0GPbWqGW-kqJZy	
