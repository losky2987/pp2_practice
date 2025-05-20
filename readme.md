# PP2 practice
This repository storage a project, that I use it to practice pp2 knowledge and skills, please do not use for another usage.

## Start command
[placeholder]

## Gradle compile command
[placeholder]

## Domain
Gate: display to show all gate information.

Flight: to make things easier, here we only have 4 attributes, flight number, destination, departure time and gate number.

Admin: administrator, manage and maintenance flights and gates.

## Arch
In this project we use Onion-Arch, it's a good arch to increase problems causes.

For domain x we have:

xEntity, xUI, xDB, xRepo, xRepoImpl, xService.

## Spring Security
This project has configured spring security, for all user you can visit /gate/{gateNumber}, for admin user you can visit
 /admin, other path will not able to visit.

To log in this app, please log in GitHub first, when you at first time visit /admin, you will be redirected to GitHub authority page,
 after logged in, you can use admin features.

## Copyrights
COPYRIGHTS © losky2987 2025. ALL RIGHTS RESERVED. This repo/project using MIT License.
