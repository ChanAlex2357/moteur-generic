# Documentation d'utilisation de GFJA

## Introduction

Ce document fournit des instructions sur la façon d'utiliser la bibliothèque Java que vous avez construite. Il couvre la configuration de base, des exemples d'utilisation et des informations supplémentaires pour vous aider à démarrer.

## Configuration

1. **Télécharger la bibliothèque**: Assurez-vous d'avoir la dernière version de la bibliothèque.
2. **Ajouter au projet**: Placez le fichier `gfja.jar` dans le répertoire `lib` de votre projet.
3. **Inclure dans le chemin de construction**: Ajoutez le `gfja.jar` au chemin de construction de votre projet.

## Utilisation

### Importer la bibliothèque

Pour utiliser la bibliothèque, vous devez importer les classes nécessaires dans votre code Java :

```java
import mg.jca.gfja.annotations.Entity;
import mg.jca.gfja.annotations.Attribute;
import mg.jca.gfja.annotations.Id;
import mg.jca.gfja.utils.EntityUtils;
import mg.jca.gfja.utils.AttributeUtils;
```

### Exemple de code

Voici un exemple simple démontrant comment utiliser les annotations :

```java
import mg.jca.gfja.annotations.Entity;
import mg.jca.gfja.annotations.Attribute;
import mg.jca.gfja.annotations.Id;
import mg.jca.gfja.utils.EntityUtils;
import mg.jca.gfja.utils.AttributeUtils;

@Entity(name = "Person")
public class Person {
    @Id
    private Long id;

    @Attribute(name = "firstName")
    private String firstName;

    @Attribute(name = "lastName")
    private String lastName;

    // Getters and setters
}

public class Main {
    public static void main(String[] args) {
        Person person = new Person();
        person.setId(1L);
        person.setFirstName("John");
        person.setLastName("Doe");

        // Vérifier si la classe est une entité
        if (EntityUtils.isEntity(Person.class) != null) {
            System.out.println("Person est une entité.");
        }

        // Obtenir l'ID de l'objet
        Object id = AttributeUtils.getId(person);
        System.out.println("ID: " + id);

        // Obtenir les attributs de l'entité
        List<Field> attributes = AttributeUtils.getAttributes(Person.class);
        for (Field field : attributes) {
            System.out.println("Attribut: " + field.getName());
        }
    }
}
```

### Fonctionnalités supplémentaires

- **Fonctionnalité 1**: Description de la fonctionnalité 1.
- **Fonctionnalité 2**: Description de la fonctionnalité 2.
- **Fonctionnalité 3**: Description de la fonctionnalité 3.

## Dépannage

Si vous rencontrez des problèmes, suivez les étapes suivantes :

1. **Vérifier les dépendances**: Assurez-vous que toutes les dépendances requises sont incluses.
2. **Consulter la documentation**: Référez-vous à la documentation officielle pour des informations détaillées.
3. **Contacter le support**: Si le problème persiste, contactez le support pour obtenir de l'aide.

## Conclusion

Ce document fournit un aperçu de base sur la façon d'utiliser la bibliothèque Java. Pour des informations plus détaillées, référez-vous à la documentation officielle ou contactez le support.