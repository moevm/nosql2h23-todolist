<template>
    <v-container>
        <v-row>
            <v-col class="mb-2">
                <h1 class="display-2 font-weight-bold mb-3">
                    Небольшое SPA приложение списка задач
                </h1>

                <p class="subheading font-weight-regular">
                    Каждое задание имеет название и&nbsp;список задач. Каждая задача состоит из&nbsp;чекбокса и&nbsp;относящейся
                    к&nbsp;нему текстовой подписи
                </p>
                <v-divider/>
            </v-col>

            <v-col
                class="mb-2"
                cols="12"
            >
                <h2 class="headline font-weight-bold mb-2">
                    Функционал
                </h2>
                <v-card>
                    <v-list-item>
                        <v-list-item-content>
                            Создание, изменение и удаление заданий
                        </v-list-item-content>
                    </v-list-item>
                    <v-divider/>
                    <v-list-item>
                        <v-list-item-content>
                            Создание, изменение и удаление задач
                        </v-list-item-content>
                    </v-list-item>
                    <v-divider/>
                    <v-list-item>
                        <v-list-item-content>
                            Фильтрация задач: все, выполненные или нет
                        </v-list-item-content>
                    </v-list-item>
                    <v-divider/>
                    <v-list-item>
                        <v-list-item-content>
                            Для каждой операции предусмотрено модальное окно подтверждения действия
                        </v-list-item-content>
                    </v-list-item>
<!--                    <v-divider/>-->
<!--                    <v-list-item>-->
<!--                        <v-list-item-content>-->
<!--                            Последнее изменение можно отменить во всплывающем уведомлении об успешной операции-->
<!--                        </v-list-item-content>-->
<!--                    </v-list-item>-->
                </v-card>
            </v-col>

            <v-col
                class="mb-2"
                cols="12"
            >
                <h2 class="headline font-weight-bold mb-2">
                    Описание решений
                </h2>
                <v-card>
<!--                    <v-list-item>-->
<!--                        <v-list-item-content>-->
<!--                            <p class="mb-0">-->
<!--                                Для интеграции Apollo использован плагин-->
<!--                                <a-->
<!--                                    href="https://apollo.vuejs.org/"-->
<!--                                    target="_blank"-->
<!--                                >-->
<!--                                    Vue&nbsp;Apollo-->
<!--                                </a>-->
<!--                            </p>-->
<!--                        </v-list-item-content>-->
<!--                    </v-list-item>-->
<!--                    <v-divider/>-->
<!--                    <v-list-item>-->
<!--                        <v-list-item-content>-->
<!--                            <p class="mb-0">-->
<!--                                Для хранения данных используется упрощенный вариант NoSQL-базы данных-->
<!--                                <a-->
<!--                                    href="https://npm.io/package/lowdb"-->
<!--                                    target="_blank"-->
<!--                                >-->
<!--                                    LowDB-->
<!--                                </a>-->
<!--                            </p>-->
<!--                        </v-list-item-content>-->
<!--                    </v-list-item>-->
<!--                    <v-divider/>-->
                    <v-list-item>
                        <v-list-item-content>
                            <p class="mb-0">
                                Верстка выполнена с&nbsp;помощью
                                <a
                                    href="https://v2.vuetifyjs.com/en/"
                                    target="_blank"
                                >
                                    Vuetify
                                </a>
                            </p>
                        </v-list-item-content>
                    </v-list-item>
                </v-card>
            </v-col>

            <v-col
                class="mb-2"
                cols="12"
            >
                <h2 class="headline font-weight-bold mb-2">
                    Планы по улучшению
                </h2>
                <v-card>
                    <v-list>
                        <v-list-item>
                            <v-list-item-content>
                                Сделать список заданий для различных проектов. На&nbsp;данный момент реализована только
                                тестовая страница проекта &rsquo;Title&rsquo;, также можно будет увидеть сразу из&nbsp;меню
                                количество завершенных задач (1/3&nbsp;&mdash; заглушка)
                            </v-list-item-content>
                        </v-list-item>
                        <v-divider/>
                        <v-list-item>
                            <v-list-item-content>
                                Доделать Home страницу, на&nbsp;которой, возможно, появятся новые функции (к&nbsp;примеру,
                                ЛК&nbsp;пользователя)
                            </v-list-item-content>
                        </v-list-item>
                    </v-list>
                </v-card>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
export default {
    name: 'AboutContent'
}
</script>
