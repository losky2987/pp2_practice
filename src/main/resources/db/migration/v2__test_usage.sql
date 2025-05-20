-- this file only use for add test data in sql tables, not for actually use

insert into admin(oauth2_id) values (65465493),
                             (11112222);

insert into gate(number) values ('A01'),
                                ('A02'),
                                ('A03'),
                                ('B01'),
                                ('B02'),
                                ('B03'),
                                ('F111'),
                                ('F223');

insert into flight(number, destination, departure_time, gate_number) values ('CA999', 'Shanghai', '09:30', 'A01'),
                                                                            ('MU220', 'Frankfurt', '11:30', 'B01'),
                                                                            ('HO1607', 'Helsinki', '09:30', 'B01'),
                                                                            ('LH928', 'Shanghai', '15:30', 'F111');
