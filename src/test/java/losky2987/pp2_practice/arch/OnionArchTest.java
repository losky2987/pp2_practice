package losky2987.pp2_practice.arch;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.GeneralCodingRules;
import losky2987.pp2_practice.Pp2PracticeApplication;
import org.springframework.beans.factory.annotation.Autowired;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;
import static com.tngtech.archunit.library.Architectures.onionArchitecture;

@AnalyzeClasses(packagesOf = Pp2PracticeApplication.class, importOptions = {ImportOption.DoNotIncludeTests.class})
public class OnionArchTest {;

    @ArchTest
    ArchRule onionArchitecture = onionArchitecture()
            .domainModels("losky2987.pp2_practice.domain..")
            .domainServices("losky2987.pp2_practice.service..")
            .applicationServices("losky2987.pp2_practice.service..")
            .adapter("controller", "losky2987.pp2_practice.controller..")
            .adapter("persistence", "losky2987.pp2_practice.persistence..");

    @ArchTest
    ArchRule controllerAccessesOnlyServices = classes()
            .that()
            .resideInAPackage("losky2987.pp2_practice.controller..")
            .should()
            .onlyHaveDependentClassesThat()
            .resideOutsideOfPackage("losky2987.pp2_practice.repository..")
            .andShould()
            .resideInAnyPackage("losky2987.pp2_practice.service..", "losky2987.pp2_practice.controller..");

    @ArchTest
    ArchRule noFieldInjection = GeneralCodingRules.NO_CLASSES_SHOULD_USE_FIELD_INJECTION;

    @ArchTest
    ArchRule noClassesWithAnnotationDeprecated = noClasses().should()
            .beAnnotatedWith(Deprecated.class);

    @ArchTest
    ArchRule noMethodsWithAnnotationDeprecated = noMethods().should()
            .beAnnotatedWith(Deprecated.class);

    @ArchTest
    ArchRule noSetterInjection = noMethods().should().beAnnotatedWith(Autowired.class);

    @ArchTest
    ArchRule DTOs_must_reside_in_a_dto_package =
            classes().that().haveNameMatching(".+DTO").should().resideInAPackage("..dto..")
                    .as("DTOs should reside in a package '..dto..'");
}
