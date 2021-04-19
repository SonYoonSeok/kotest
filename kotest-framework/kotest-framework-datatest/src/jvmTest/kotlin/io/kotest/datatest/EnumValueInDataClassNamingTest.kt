package io.kotest.datatest

import io.kotest.core.spec.Spec
import io.kotest.core.spec.style.FunSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.matchers.shouldBe

class EnumValueInDataClassNamingTest : FunSpec() {

   private val names = mutableListOf<String>()

   override fun beforeSpec(spec: Spec) {
      names.clear()
   }

   override fun afterAny(testCase: TestCase, result: TestResult) {
      names.add(testCase.description.displayName())
   }

   override fun afterSpec(spec: Spec) {
      names shouldBe listOf(
         "PythTriple(a=Three, b=Four, c=Five)",
         "PythTriple(a=Four, b=Three, c=Five)",
         "PythTriple(a=Four, b=Three, c=Five) (1)",
         "data class with enum with field",
         "FooClass(a=Bar1, b=Bar2)",
         "FooClass(a=Bar1, b=Bar2) (1)",
         "data class with enum with class field"
      )
   }

   init {
      context("data class with enum with field") {
         forAll(
            PythTriple(PythagNumber.Three, PythagNumber.Four, PythagNumber.Five),
            PythTriple(PythagNumber.Four, PythagNumber.Three, PythagNumber.Five),
            PythTriple(PythagNumber.Four, PythagNumber.Three, PythagNumber.Five),
         ) {}
      }

      context("data class with enum with class field") {
         forAll(
            FooClass(a = Bar.Bar1, b = Bar.Bar2),
            FooClass(a = Bar.Bar1, b = Bar.Bar2),
         ) {}
      }
   }
}

enum class PythagNumber(val num: Int) {
   Three(3), Four(4), Five(5);
}

data class PythTriple(val a: PythagNumber, val b: PythagNumber, val c: PythagNumber)

data class FooClass(val a: Bar, val b: Bar)
class Baz(val message: String)

enum class Bar(val baz: Baz) {
   Bar1(Baz("Baz1")),
   Bar2(Baz("Baz2"))
}
