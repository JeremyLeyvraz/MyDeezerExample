# My Deezer example

## Résumé
Application similaire à Deezer.

Elle lit des pistes audio enregistrées en local.
Il possible de jouer une piste, de la mettre en pause, de passer à la suivante ou à la précédente.

L'application gère le format portrait/paysage.
L'application a été testée sur téléphone et sur tablette.

La piste est jouée par un foreground service, avec une notification associée.

Warning: Pour le moment, pour stopper le service, il faut force stop l'application.

## Matériels utilisés pour les tests

Téléphone: Samsung Galaxy Z Flip 5 - Android 14
Tablette: Samsung Galaxy Tab S6 Lite - Android 12

# TODO

## Features
- Foreground notification custom => design mini lecteur
- Ajout d'un écran pour choisir des playlists différentes 
- Lecteur: ajout de la fonction "repeat one"
- Lecteur: ajout de la fonction "repeat all"
- Permettre l'arrêt total de l'application sans passer par "force stop"

## Refactoring
- Utiliser type et color du package theme
- Utilisez des images de meilleure qualité

## Bugs
- Foreground notification: (fermer app + clic notif) plusieurs fois => clic sur back plusieurs fois possible => vérifier la pile des activités
