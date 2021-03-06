
    create table Attempt (
       id bigint generated by default as identity,
        openedOn timestamp,
        sendOn timestamp,
        stage varchar(255),
        trickedOn timestamp,
        phishingTarget_id bigint,
        sender_id bigint,
        spoofTarget_id bigint,
        template_id bigint,
        primary key (id)
    );

    create table EmailTemplate (
       id bigint generated by default as identity,
        createdOn timestamp not null,
        lastModifiedOn timestamp not null,
        name varchar(255) not null,
        html varchar(1000000) not null,
        primary key (id)
    );

    create table EmailTemplate_Parameter (
       EmailTemplate_id bigint not null,
        parameters_id bigint not null,
        primary key (EmailTemplate_id, parameters_id)
    );

    create table Parameter (
       id bigint generated by default as identity,
        name varchar(255) not null,
        sourceType varchar(255) not null,
        primary key (id)
    );

    create table ParameterContainerEntity (
       id bigint generated by default as identity,
        createdOn timestamp not null,
        lastModifiedOn timestamp not null,
        primary key (id)
    );

    create table ParameterInstance (
       id bigint generated by default as identity,
        value varchar(255) not null,
        container_id bigint not null,
        parameter_id bigint not null,
        primary key (id)
    );

    create table PhishingTarget (
       emailAddress varchar(255) not null,
        id bigint not null,
        primary key (id)
    );

    create table Sender (
       id bigint generated by default as identity,
        createdOn timestamp not null,
        lastModifiedOn timestamp not null,
        name varchar(255) not null,
        password varchar(255) not null,
        server_id bigint,
        primary key (id)
    );

    create table SenderServer (
       id bigint generated by default as identity,
        createdOn timestamp not null,
        lastModifiedOn timestamp not null,
        name varchar(255) not null,
        host varchar(255) not null,
        port integer not null,
        primary key (id)
    );

    create table SpoofTarget (
       emailAddress varchar(255) not null,
        id bigint not null,
        primary key (id)
    );

    alter table EmailTemplate 
       add constraint UK_r9g3va2463hbkq4cglmjjmjxo unique (name);

    alter table Parameter 
       add constraint UKrw0ticql6fgxpw15y481tmpv7 unique (name, sourceType);

    alter table ParameterInstance 
       add constraint UK3uox5ar2tygm63hrulqxtjtpi unique (parameter_id, container_id);

    alter table PhishingTarget 
       add constraint UK_t31vfpaimg746lpygo9y08jkc unique (emailAddress);

    alter table Sender 
       add constraint UK_dic5ri34wuf02g96ka601ucy2 unique (name);

    alter table SenderServer 
       add constraint UKcr5p5y2fw4r30yctj33g54uwt unique (port, host);

    alter table SenderServer 
       add constraint UK_jbwjjl7guqmim2n6c8cot7d61 unique (name);

    alter table SpoofTarget 
       add constraint UK_843v7n5rjcpoturo37kplm6ai unique (emailAddress);

    alter table Attempt 
       add constraint FKc5chjncg0vvlh7gunfjl0dsy4 
       foreign key (phishingTarget_id) 
       references PhishingTarget;

    alter table Attempt 
       add constraint FKfuygakxu77tmxobdr0idhemp8 
       foreign key (sender_id) 
       references Sender;

    alter table Attempt 
       add constraint FKmpy7gdt2eqkeb3e1ocnpudhun 
       foreign key (spoofTarget_id) 
       references SpoofTarget;

    alter table Attempt 
       add constraint FKd0w3tu208bgutx4u0yejksy1q 
       foreign key (template_id) 
       references EmailTemplate;

    alter table EmailTemplate_Parameter 
       add constraint FK25idopob1fqwq4angpuj8ee3f 
       foreign key (parameters_id) 
       references Parameter;

    alter table EmailTemplate_Parameter 
       add constraint FKtc8blwfduhxgv2gkytn4bxyib 
       foreign key (EmailTemplate_id) 
       references EmailTemplate;

    alter table ParameterInstance 
       add constraint FKcuc94c0da6yogujgdam694qv4 
       foreign key (container_id) 
       references ParameterContainerEntity;

    alter table ParameterInstance 
       add constraint FKslf4wwxmpl1jm4x176ioxm1xr 
       foreign key (parameter_id) 
       references Parameter;

    alter table PhishingTarget 
       add constraint FKs8bwoq24alchckule2s24wx75 
       foreign key (id) 
       references ParameterContainerEntity;

    alter table Sender 
       add constraint FKn792mqtwofbcxeq253tjylew2 
       foreign key (server_id) 
       references SenderServer;

    alter table SpoofTarget 
       add constraint FKlu758s89ow91qjbqkvey3mps0 
       foreign key (id) 
       references ParameterContainerEntity;
