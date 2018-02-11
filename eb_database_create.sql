create table attribute (id bigint not null auto_increment, type varchar(255), value integer, primary key (id)) ENGINE=InnoDB;
create table card (id bigint not null auto_increment, cost integer, name varchar(255), primary key (id)) ENGINE=InnoDB;
create table card_attributes (card_id bigint not null, attributes_id bigint not null) ENGINE=InnoDB;
create table game (id bigint not null auto_increment, turn integer, game_phase_id bigint, primary key (id)) ENGINE=InnoDB;
create table game_game_players (game_id bigint not null, game_players_id bigint not null) ENGINE=InnoDB;
create table game_card (id bigint not null auto_increment, cost integer, name varchar(255), primary key (id)) ENGINE=InnoDB;
create table game_card_attributes (game_card_id bigint not null, attributes_id bigint not null) ENGINE=InnoDB;
create table game_command (id bigint not null auto_increment, payload varchar(255), type varchar(255), user_id bigint, primary key (id)) ENGINE=InnoDB;
create table game_phase (id bigint not null auto_increment, type varchar(255), primary key (id)) ENGINE=InnoDB;
create table game_phase_end_phase_game_player_ids (game_phase_id bigint not null, end_phase_game_player_ids bigint) ENGINE=InnoDB;
create table game_player (id bigint not null auto_increment, attack integer, energy integer, health integer, is_bot bit, user_id bigint, current_game_question_id bigint, primary key (id)) ENGINE=InnoDB;
create table game_player_deck (game_player_id bigint not null, deck_id bigint not null) ENGINE=InnoDB;
create table game_player_game_questions (game_player_id bigint not null, game_questions_id bigint not null) ENGINE=InnoDB;
create table game_player_hand (game_player_id bigint not null, hand_id bigint not null) ENGINE=InnoDB;
create table game_player_permanents (game_player_id bigint not null, permanents_id bigint not null) ENGINE=InnoDB;
create table game_question (id bigint not null auto_increment, end_date datetime, performance integer, selected_answer varchar(255), start_date datetime, turn integer, question_id bigint, primary key (id)) ENGINE=InnoDB;
create table question (id bigint not null auto_increment, affinity varchar(255), average_answer_time integer, category varchar(255), correct_answer varchar(255), title varchar(255), primary key (id)) ENGINE=InnoDB;
create table question_potential_answers (question_id bigint not null, potential_answers varchar(255)) ENGINE=InnoDB;
create table user (id bigint not null auto_increment, game_id bigint, name varchar(255), primary key (id)) ENGINE=InnoDB;
create table user_card (user_id bigint not null, card_id bigint not null) ENGINE=InnoDB;
alter table game_game_players add constraint UK_mgac9wbbgjn430hqemku1a7fr unique (game_players_id);
alter table game_player_deck add constraint UK_ry48mdw5lld2kescgs0pdw74 unique (deck_id);
alter table game_player_game_questions add constraint UK_haeo2mpiiia234rhttgkmdj8c unique (game_questions_id);
alter table game_player_hand add constraint UK_747wyy4t6bayx1ubj684vwju0 unique (hand_id);
alter table game_player_permanents add constraint UK_752p0ao4ftj8aaywps0yoogl8 unique (permanents_id);
alter table card_attributes add constraint FKqjxmf21eqca0khvs4n7lj687f foreign key (attributes_id) references attribute (id);
alter table card_attributes add constraint FKfldm5qc9em4di41s7w4kw6cn2 foreign key (card_id) references card (id);
alter table game add constraint FKdpuu45nv60v7quk28nk5xm15y foreign key (game_phase_id) references game_phase (id);
alter table game_game_players add constraint FKlj6jgkdiv7xfxms7fwu5fw8ic foreign key (game_players_id) references game_player (id);
alter table game_game_players add constraint FKl0nvt0y6e2y9wxm245wl9t7xl foreign key (game_id) references game (id);
alter table game_card_attributes add constraint FK36sohjsm8gyhmkjtkcvv8djhl foreign key (attributes_id) references attribute (id);
alter table game_card_attributes add constraint FKo3a0446bw8i9laq5a0fxl0ow foreign key (game_card_id) references game_card (id);
alter table game_phase_end_phase_game_player_ids add constraint FK80ktp02svo82jns2r8ysg0fxe foreign key (game_phase_id) references game_phase (id);
alter table game_player add constraint FK5xf9whn2gf8234n3p28ascy8y foreign key (current_game_question_id) references game_question (id);
alter table game_player_deck add constraint FKd9nkft0o2wnvn6vqk5hxj5j5g foreign key (deck_id) references game_card (id);
alter table game_player_deck add constraint FKde8sivysm4dy9opbbkpvct5x foreign key (game_player_id) references game_player (id);
alter table game_player_game_questions add constraint FK7i1c3u0darca3fw6px2skvsk1 foreign key (game_questions_id) references game_question (id);
alter table game_player_game_questions add constraint FK50ao9604emcox6t1dqvb3di57 foreign key (game_player_id) references game_player (id);
alter table game_player_hand add constraint FKoli66hm93xs1uf1aodwtnfdlb foreign key (hand_id) references game_card (id);
alter table game_player_hand add constraint FKmmm3icvt19si64ausuxeugnx1 foreign key (game_player_id) references game_player (id);
alter table game_player_permanents add constraint FKiqrl01xeorjpr58go5wibyo1o foreign key (permanents_id) references game_card (id);
alter table game_player_permanents add constraint FKrjo8uex8xu89ci69u4jly53fq foreign key (game_player_id) references game_player (id);
alter table game_question add constraint FK7781x5fgo05yokpul4e1km1cw foreign key (question_id) references question (id);
alter table question_potential_answers add constraint FKb4k65lpj0au32ityu0xcsxyx1 foreign key (question_id) references question (id);
alter table user_card add constraint FK441bl0wnj3hxcj8c18lyxw9e1 foreign key (card_id) references card (id);
alter table user_card add constraint FKmeit1ul0skwyx74bewpxx8gml foreign key (user_id) references user (id);