package com.example.data.repository

import com.example.data.model.*

object CppContentRepository {

  val levels: List<CppLevel> = listOf(
    CppLevel(
      id = 1,
      title = "Level 01: Programming Fundamentals",
      subtitle = "How C++ works under the hood: Compilers, Linkers, and Binaries",
      tag = "Fundamentals",
      icon = "memory",
      modules = listOf(
        CppModule(
          id = "mod_01",
          title = "How C++ Executes",
          description = "From source text to machine code execution on the CPU.",
          lessons = listOf(
            CppLesson(
              id = "les_01_01",
              levelId = 1,
              moduleId = "mod_01",
              title = "What is C++ & The Build Process",
              durationMinutes = 8,
              overview = "Understand why C++ powers high-performance systems and how code transforms through Preprocessor, Compiler, Linker, and Executable.",
              visualizerType = VisualizerType.COMPILER_PIPELINE,
              explanation = "C++ was designed by Bjarne Stroustrup in 1979 as an extension of C with classes. Unlike interpreted languages (Python/JS) that run on an interpreter VM, C++ compiles directly to native CPU machine instructions.\n\nThe build pipeline:\n1. Preprocessor: resolves #include and #define directives.\n2. Compiler: translates C++ code into assembly and machine object code (.o/.obj).\n3. Linker: resolves symbol addresses across files and standard libraries into a runnable binary (.exe/ELF).\n4. Loader: loads binary segments into RAM to begin CPU execution.",
              deepDive = "Zero-cost abstractions: What you don't use, you don't pay for. And what you do use, you couldn't hand code any better.",
              codeExample = """// Level 01: The Build Process
#include <iostream>

int main() {
    std::cout << "Welcome to Learn C++ by Awiskar Acharya!" << std::endl;
    std::cout << "Machine architecture: 64-bit native binary" << std::endl;
    return 0;
}""",
              expectedOutput = "Welcome to Learn C++ by Awiskar Acharya!\nMachine architecture: 64-bit native binary",
              quiz = listOf(
                QuizQuestion(
                  id = "q_01_01",
                  question = "Which build pipeline stage merges object files and resolves function symbol addresses?",
                  options = listOf("Preprocessor", "Compiler", "Linker", "CPU Loader"),
                  correctOptionIndex = 2,
                  explanation = "The Linker combines translation units (.o) and resolves symbol references into the final executable."
                )
              )
            ),
            CppLesson(
              id = "les_01_02",
              levelId = 1,
              moduleId = "mod_01",
              title = "Writing Your First C++ Program",
              durationMinutes = 6,
              overview = "Anatomy of main(), iostream, std::cout, and standard stream flush.",
              visualizerType = VisualizerType.COMPILER_PIPELINE,
              explanation = "Every C++ executable has exactly one global main() entry point. '#include <iostream>' is a preprocessor header inclusion for input/output streams.\n'std::cout' writes to standard output, and 'std::endl' inserts a newline character while flushing the output stream buffer.",
              codeExample = """#include <iostream>

int main() {
    // std::cout is the standard output stream
    std::cout << "Hello, Modern C++ World!" << '\n';
    std::cout << "Learning fast with hands-on code." << std::endl;
    return 0;
}""",
              expectedOutput = "Hello, Modern C++ World!\nLearning fast with hands-on code.",
              quiz = listOf(
                QuizQuestion(
                  id = "q_01_02",
                  question = "What is the primary operational difference between '\\n' and 'std::endl'?",
                  options = listOf("std::endl also flushes the stream buffer", "std::endl is faster than \\n", "\\n works only in C", "There is no difference"),
                  correctOptionIndex = 0,
                  explanation = "std::endl writes '\\n' and explicitly calls stream.flush(), which incurs an I/O sync cost."
                )
              )
            )
          )
        )
      )
    ),
    CppLevel(
      id = 2,
      title = "Level 02: Variables & Type System",
      subtitle = "Memory footprint, primitive types, auto, and constexpr",
      tag = "Core Types",
      icon = "data_object",
      modules = listOf(
        CppModule(
          id = "mod_02",
          title = "Data Representation in Memory",
          description = "Integers, floating point, char, bool, auto, and const correctness.",
          lessons = listOf(
            CppLesson(
              id = "les_02_01",
              levelId = 2,
              moduleId = "mod_02",
              title = "Variables & Memory Allocation",
              durationMinutes = 7,
              overview = "How variables reserve named memory slots on the stack with strict typing.",
              visualizerType = VisualizerType.VARIABLE_MEMORY,
              explanation = "When you write 'int score = 100;', the compiler allocates 4 contiguous bytes on the execution stack and binds the identifier 'score' to that memory address.\nC++ is statically and strongly typed: the compiler verifies all operations at compile time.",
              codeExample = """#include <iostream>

int main() {
    int score = 100;
    double accuracy = 98.5;
    char grade = 'A';
    bool passed = true;

    std::cout << "Score: " << score << " (" << sizeof(score) << " bytes)\n";
    std::cout << "Accuracy: " << accuracy << " (" << sizeof(accuracy) << " bytes)\n";
    std::cout << "Grade: " << grade << " (" << sizeof(grade) << " byte)\n";
    std::cout << "Passed: " << std::boolalpha << passed << '\n';
    return 0;
}""",
              expectedOutput = "Score: 100 (4 bytes)\nAccuracy: 98.5 (8 bytes)\nGrade: A (1 byte)\nPassed: true"
            ),
            CppLesson(
              id = "les_02_02",
              levelId = 2,
              moduleId = "mod_02",
              title = "const, constexpr & auto Type Deduction",
              durationMinutes = 8,
              overview = "Compile-time evaluation with constexpr and modern auto keyword.",
              visualizerType = VisualizerType.VARIABLE_MEMORY,
              explanation = "'constexpr' specifies that the value of an object or return value of a function can be evaluated at compile time. 'auto' asks the compiler to deduce the type from the initializer, eliminating redundant verbosity.",
              codeExample = """#include <iostream>

int main() {
    constexpr double PI = 3.14159265359;
    constexpr int BUFFER_SIZE = 1024 * 4;
    auto counter = 42;          // deduced as int
    auto ratio = 0.75f;         // deduced as float

    std::cout << "PI: " << PI << "\nBuffer: " << BUFFER_SIZE << " bytes\n";
    std::cout << "Counter: " << counter << ", Ratio: " << ratio << '\n';
    return 0;
}""",
              expectedOutput = "PI: 3.14159\nBuffer: 4096 bytes\nCounter: 42, Ratio: 0.75"
            )
          )
        )
      )
    ),
    CppLevel(
      id = 3,
      title = "Level 03: Control Flow & Functions",
      subtitle = "Branching, loops, call stacks, and function overloading",
      tag = "Control",
      icon = "account_tree",
      modules = listOf(
        CppModule(
          id = "mod_03",
          title = "Logic & Functions",
          description = "Conditionals, switch, modern range-for loops, and stack frames.",
          lessons = listOf(
            CppLesson(
              id = "les_03_01",
              levelId = 3,
              moduleId = "mod_03",
              title = "Conditionals & The Evaluation Tree",
              durationMinutes = 6,
              overview = "Branch prediction, if-else chains, and switch-case jump tables.",
              visualizerType = VisualizerType.CONDITION_BRANCH,
              explanation = "Conditionals evaluate boolean expressions to determine branch flow. In modern C++17, you can even use init-statements inside if: 'if (int val = compute(); val > 0)'.",
              codeExample = """#include <iostream>

int main() {
    int power = 85;

    if (power > 90) {
        std::cout << "Status: OVERLOAD\n";
    } else if (power >= 50) {
        std::cout << "Status: OPTIMAL (" << power << "%)\n";
    } else {
        std::cout << "Status: LOW POWER\n";
    }
    return 0;
}""",
              expectedOutput = "Status: OPTIMAL (85%)"
            ),
            CppLesson(
              id = "les_03_02",
              levelId = 3,
              moduleId = "mod_03",
              title = "Loops & Range-Based Iteration",
              durationMinutes = 7,
              overview = "Standard for loops, while loops, and modern range-based for loops.",
              visualizerType = VisualizerType.LOOP_ANIMATION,
              explanation = "C++11 introduced range-based for loops: 'for (const auto& item : collection)' which iterates smoothly over any container providing begin() and end() iterators.",
              codeExample = """#include <iostream>

int main() {
    int primes[] = {2, 3, 5, 7, 11};

    std::cout << "Range-based iteration:\n";
    for (int p : primes) {
        std::cout << p << " ";
    }
    std::cout << "\nSummation complete!\n";
    return 0;
}""",
              expectedOutput = "Range-based iteration:\n2 3 5 7 11 \nSummation complete!"
            ),
            CppLesson(
              id = "les_03_03",
              levelId = 3,
              moduleId = "mod_03",
              title = "Functions & Call Stack Activation",
              durationMinutes = 9,
              overview = "Stack frames, parameters, return values, and function overloading.",
              visualizerType = VisualizerType.FUNCTION_STACK,
              explanation = "When a function is called, the CPU pushes a new Stack Frame containing return address, caller base pointer, arguments, and local variables. Overloading allows multiple functions with the same name but different parameter signatures.",
              codeExample = """#include <iostream>

int multiply(int a, int b) {
    return a * b;
}

double multiply(double a, double b) {
    return a * b;
}

int main() {
    std::cout << "Int multiply: " << multiply(6, 7) << '\n';
    std::cout << "Double multiply: " << multiply(3.5, 2.0) << '\n';
    return 0;
}""",
              expectedOutput = "Int multiply: 42\nDouble multiply: 7"
            )
          )
        )
      )
    ),
    CppLevel(
      id = 4,
      title = "Level 04: Pointers & References",
      subtitle = "Addresses, dereferencing, pointer arithmetic, and reference aliasing",
      tag = "Pointers",
      icon = "link",
      modules = listOf(
        CppModule(
          id = "mod_04",
          title = "Mastering Memory Addressing",
          description = "Direct hardware memory access, pointers vs references, and const correctness.",
          lessons = listOf(
            CppLesson(
              id = "les_04_01",
              levelId = 4,
              moduleId = "mod_04",
              title = "Pointers & The Dereference Operator",
              durationMinutes = 10,
              overview = "Memory addresses (&), pointer variables (*ptr), and reading/writing raw memory.",
              visualizerType = VisualizerType.POINTER_BOXES,
              explanation = "A pointer is a variable whose value is the memory address of another variable.\n'&' is the address-of operator.\n'*' on a pointer dereferences it, accessing the value stored at that address.\nModern C++ strongly recommends 'nullptr' instead of NULL or 0.",
              codeExample = """#include <iostream>

int main() {
    int target = 42;
    int* ptr = &target; // ptr holds the memory address of target

    std::cout << "Value of target: " << target << '\n';
    std::cout << "Address of target: " << ptr << '\n';
    std::cout << "Dereferenced *ptr: " << *ptr << '\n';

    *ptr = 99; // mutate target through pointer
    std::cout << "Mutated target via *ptr: " << target << '\n';
    return 0;
}""",
              expectedOutput = "Value of target: 42\nAddress of target: 0x7ffd9a2b\nDereferenced *ptr: 42\nMutated target via *ptr: 99"
            ),
            CppLesson(
              id = "les_04_02",
              levelId = 4,
              moduleId = "mod_04",
              title = "Pointers vs References",
              durationMinutes = 8,
              overview = "Why references are non-null aliases and when to prefer pass-by-const-reference.",
              visualizerType = VisualizerType.POINTER_VS_REFERENCE,
              explanation = "A reference ('int& ref = target') is an alias for an existing object. Key differences:\n1. References cannot be null.\n2. References must be initialized upon declaration.\n3. References cannot be reseated to point to another object.\nUse 'const T&' for function arguments to avoid costly copies without risking mutations.",
              codeExample = """#include <iostream>

void modifyByRef(int& ref) {
    ref += 10;
}

void printConstRef(const std::string& text) {
    // No copy made! Read-only access.
    std::cout << "Read via const ref: " << text << '\n';
}

int main() {
    int val = 20;
    modifyByRef(val);
    std::cout << "After modifyByRef: " << val << '\n';

    std::string message = "Learn C++ with Awiskar Acharya";
    printConstRef(message);
    return 0;
}""",
              expectedOutput = "After modifyByRef: 30\nRead via const ref: Learn C++ with Awiskar Acharya"
            )
          )
        )
      )
    ),
    CppLevel(
      id = 5,
      title = "Level 05: Memory Management & Dynamic Allocation",
      subtitle = "Stack vs Heap, new/delete, memory leaks, and RAII principles",
      tag = "Memory",
      icon = "layers",
      modules = listOf(
        CppModule(
          id = "mod_05",
          title = "Stack, Heap & RAII",
          description = "Automatic storage duration, dynamic heap allocation, and safe resource ownership.",
          lessons = listOf(
            CppLesson(
              id = "les_05_01",
              levelId = 5,
              moduleId = "mod_05",
              title = "Stack vs Heap Allocation",
              durationMinutes = 9,
              overview = "Contrasting fast LIFO stack memory with flexible dynamic heap memory.",
              visualizerType = VisualizerType.STACK_VS_HEAP,
              explanation = "Stack: Fast, LIFO, managed automatically by compiler. Limited size (typically 1-8 MB). Variables expire when exiting scope.\nHeap: Slower, manual lifetime, shared across scopes, much larger size (limited by RAM). Allocated via 'new' and must be freed with 'delete' to prevent memory leaks.",
              codeExample = """#include <iostream>

int main() {
    // Stack allocation
    int stackVal = 50;

    // Heap allocation
    int* heapPtr = new int(100);
    int* heapArray = new int[3]{10, 20, 30};

    std::cout << "Stack val: " << stackVal << '\n';
    std::cout << "Heap val: " << *heapPtr << '\n';
    std::cout << "Heap array[1]: " << heapArray[1] << '\n';

    // Must release heap memory!
    delete heapPtr;
    delete[] heapArray;
    std::cout << "Heap memory cleanly deleted!\n";
    return 0;
}""",
              expectedOutput = "Stack val: 50\nHeap val: 100\nHeap array[1]: 20\nHeap memory cleanly deleted!"
            ),
            CppLesson(
              id = "les_05_02",
              levelId = 5,
              moduleId = "mod_05",
              title = "RAII: Resource Acquisition Is Initialization",
              durationMinutes = 8,
              overview = "The bedrock philosophy of C++: bind resource lifetime to object scope.",
              visualizerType = VisualizerType.RAII_RESOURCE_CYCLE,
              explanation = "RAII guarantees that resources (heap memory, file handles, mutex locks) are acquired in constructors and deterministically released in destructors, even if exceptions are thrown.",
              codeExample = """#include <iostream>

class ScopedResource {
public:
    ScopedResource(const std::string& name) : resName(name) {
        std::cout << "[ACQUIRED] " << resName << " allocated.\n";
    }
    ~ScopedResource() {
        std::cout << "[RELEASED] " << resName << " destroyed.\n";
    }
    void doWork() const {
        std::cout << "Working with " << resName << "...\n";
    }
private:
    std::string resName;
};

int main() {
    {
        ScopedResource res("DatabaseSocket");
        res.doWork();
    } // Exiting scope automatically invokes destructor!
    std::cout << "Scope closed. Resource was safely freed.\n";
    return 0;
}""",
              expectedOutput = "[ACQUIRED] DatabaseSocket allocated.\nWorking with DatabaseSocket...\n[RELEASED] DatabaseSocket destroyed.\nScope closed. Resource was safely freed."
            )
          )
        )
      )
    ),
    CppLevel(
      id = 6,
      title = "Level 06: Object-Oriented Programming (OOP)",
      subtitle = "Classes, encapsulation, constructors, destructors, and this pointer",
      tag = "OOP",
      icon = "category",
      modules = listOf(
        CppModule(
          id = "mod_06",
          title = "Classes & Objects",
          description = "Encapsulating state and behavior into modern C++ classes.",
          lessons = listOf(
            CppLesson(
              id = "les_06_01",
              levelId = 6,
              moduleId = "mod_06",
              title = "Classes, Access Modifiers & Members",
              durationMinutes = 10,
              overview = "Declaring classes, private invariants, public interfaces, and member methods.",
              visualizerType = VisualizerType.CLASS_OBJECT,
              explanation = "A class is a blueprint defining data members (state) and member functions (behavior). Access modifiers enforce encapsulation:\n- 'private': accessible only inside class methods.\n- 'public': accessible by any caller.\n- 'protected': accessible by class and derived classes.",
              codeExample = """#include <iostream>
#include <string>

class Student {
private:
    std::string name;
    int rollNumber;
    double gpa;

public:
    Student(std::string n, int r, double g)
        : name(n), rollNumber(r), gpa(g) {}

    void display() const {
        std::cout << "Student: " << name
                  << " | Roll: " << rollNumber
                  << " | GPA: " << gpa << '\n';
    }

    bool isHonors() const {
        return gpa >= 3.8;
    }
};

int main() {
    Student s1("Awiskar", 101, 3.95);
    s1.display();
    std::cout << "Honors: " << (s1.isHonors() ? "Yes" : "No") << '\n';
    return 0;
}""",
              expectedOutput = "Student: Awiskar | Roll: 101 | GPA: 3.95\nHonors: Yes"
            ),
            CppLesson(
              id = "les_06_02",
              levelId = 6,
              moduleId = "mod_06",
              title = "Constructors, Delegating & Destructors",
              durationMinutes = 9,
              overview = "Constructor lifecycle, member initializer lists, and destructor cleanup.",
              visualizerType = VisualizerType.CONSTRUCTOR_LIFECYCLE,
              explanation = "Constructors initialize objects. Using Member Initializer Lists (': member(val)') is more efficient than assignment inside the body because it avoids default-construction followed by copy-assignment.",
              codeExample = """#include <iostream>

class Vector2D {
private:
    double x, y;
public:
    // Default constructor delegating
    Vector2D() : Vector2D(0.0, 0.0) {}

    // Parameterized constructor
    Vector2D(double xVal, double yVal) : x(xVal), y(yVal) {
        std::cout << "Vector2D(" << x << ", " << y << ") created\n";
    }

    ~Vector2D() {
        std::cout << "Vector2D(" << x << ", " << y << ") destroyed\n";
    }
};

int main() {
    Vector2D v1(3.0, 4.0);
    Vector2D v2; // defaults to 0, 0
    return 0;
}""",
              expectedOutput = "Vector2D(3, 4) created\nVector2D(0, 0) created\nVector2D(0, 0) destroyed\nVector2D(3, 4) destroyed"
            )
          )
        )
      )
    ),
    CppLevel(
      id = 7,
      title = "Level 07: Inheritance & Polymorphism",
      subtitle = "Class hierarchies, virtual functions, vtables, and abstract interfaces",
      tag = "OOP Advanced",
      icon = "account_tree",
      modules = listOf(
        CppModule(
          id = "mod_07",
          title = "Inheritance & Dynamic Dispatch",
          description = "Base classes, override keyword, virtual destructors, and pure virtual contracts.",
          lessons = listOf(
            CppLesson(
              id = "les_07_01",
              levelId = 7,
              moduleId = "mod_07",
              title = "Inheritance & Base Class Extension",
              durationMinutes = 8,
              overview = "Reusing and specializing behavior with public inheritance.",
              visualizerType = VisualizerType.INHERITANCE_TREE,
              explanation = "Inheritance allows a derived class to inherit data and methods from a base class. Constructors run Base -> Derived; Destructors run Derived -> Base.",
              codeExample = """#include <iostream>
#include <string>

class Vehicle {
protected:
    std::string brand;
public:
    Vehicle(std::string b) : brand(b) {}
    void honk() const {
        std::cout << brand << " says: Beep beep!\n";
    }
};

class ElectricCar : public Vehicle {
private:
    int batteryKWh;
public:
    ElectricCar(std::string b, int kwh)
        : Vehicle(b), batteryKWh(kwh) {}

    void showBattery() const {
        std::cout << brand << " battery: " << batteryKWh << " kWh\n";
    }
};

int main() {
    ElectricCar tesla("Tesla Model 3", 75);
    tesla.honk();
    tesla.showBattery();
    return 0;
}""",
              expectedOutput = "Tesla Model 3 says: Beep beep!\nTesla Model 3 battery: 75 kWh"
            ),
            CppLesson(
              id = "les_07_02",
              levelId = 7,
              moduleId = "mod_07",
              title = "Virtual Functions & The vtable",
              durationMinutes = 11,
              overview = "Runtime polymorphism, virtual tables, dynamic dispatch, and pure virtual functions.",
              visualizerType = VisualizerType.POLYMORPHISM_DISPATCH,
              explanation = "Declaring a function 'virtual' in the base class enables dynamic dispatch via a virtual method table (vtable). At runtime, the actual object's overridden method is executed even when accessed via a Base pointer or reference.",
              codeExample = """#include <iostream>
#include <memory>
#include <vector>

class Shape {
public:
    virtual ~Shape() = default; // CRITICAL: Always make base destructors virtual!
    virtual void draw() const = 0; // Pure virtual function
};

class Circle : public Shape {
public:
    void draw() const override {
        std::cout << "Drawing a Circle (radius: 5)\n";
    }
};

class Rectangle : public Shape {
public:
    void draw() const override {
        std::cout << "Drawing a Rectangle (width: 4, height: 6)\n";
    }
};

int main() {
    std::vector<std::unique_ptr<Shape>> shapes;
    shapes.push_back(std::make_unique<Circle>());
    shapes.push_back(std::make_unique<Rectangle>());

    for (const auto& s : shapes) {
        s->draw(); // Dynamically dispatched through vtable!
    }
    return 0;
}""",
              expectedOutput = "Drawing a Circle (radius: 5)\nDrawing a Rectangle (width: 4, height: 6)"
            )
          )
        )
      )
    ),
    CppLevel(
      id = 8,
      title = "Level 08: Templates & Generic Programming",
      subtitle = "Type independence, template functions, class templates, and specialization",
      tag = "Templates",
      icon = "auto_awesome",
      modules = listOf(
        CppModule(
          id = "mod_08",
          title = "Generic Programming",
          description = "Writing compile-time polymorphic code that works with any data type.",
          lessons = listOf(
            CppLesson(
              id = "les_08_01",
              levelId = 8,
              moduleId = "mod_08",
              title = "Function & Class Templates",
              durationMinutes = 9,
              overview = "How compiler instantiates specialized code for each concrete type used.",
              visualizerType = VisualizerType.TEMPLATE_INSTANTIATION,
              explanation = "Templates allow you to write generic blueprints. When you call 'maximum(5, 10)', the compiler generates a specific 'int' version. When you call 'maximum(3.14, 2.71)', it generates a 'double' version.",
              codeExample = """#include <iostream>
#include <string>

template<typename T>
T maximum(T a, T b) {
    return (a > b) ? a : b;
}

template<typename T, int Size>
class StaticBuffer {
    T data[Size];
public:
    int capacity() const { return Size; }
};

int main() {
    std::cout << "Max int: " << maximum(15, 42) << '\n';
    std::cout << "Max double: " << maximum(3.1415, 2.718) << '\n';
    std::cout << "Max string: " << maximum(std::string("Apple"), std::string("Zebra")) << '\n';

    StaticBuffer<int, 64> buffer;
    std::cout << "StaticBuffer capacity: " << buffer.capacity() << '\n';
    return 0;
}""",
              expectedOutput = "Max int: 42\nMax double: 3.1415\nMax string: Zebra\nStaticBuffer capacity: 64"
            )
          )
        )
      )
    ),
    CppLevel(
      id = 9,
      title = "Level 09: Standard Template Library (STL)",
      subtitle = "vector, map, set, unordered_map, iterators, and std::algorithms",
      tag = "STL",
      icon = "inventory_2",
      modules = listOf(
        CppModule(
          id = "mod_09",
          title = "STL Containers & Algorithms",
          description = "Mastering vectors, hash maps, binary search trees, and transform algorithms.",
          lessons = listOf(
            CppLesson(
              id = "les_09_01",
              levelId = 9,
              moduleId = "mod_09",
              title = "std::vector: Dynamic Contiguous Arrays",
              durationMinutes = 10,
              overview = "Under the hood of std::vector: size vs capacity, geometric reallocation, and iterators.",
              visualizerType = VisualizerType.VECTOR_BUFFER,
              explanation = "std::vector is the default sequence container in C++. It stores elements contiguously in memory. When capacity is exceeded, it allocates a new buffer (typically 1.5x or 2x size), moves existing elements, and frees old memory.\n- size(): current element count.\n- capacity(): allocated memory slots before reallocation.",
              codeExample = """#include <iostream>
#include <vector>

int main() {
    std::vector<int> nums;
    std::cout << "Init - Size: " << nums.size() << " | Capacity: " << nums.capacity() << '\n';

    for (int i = 1; i <= 5; ++i) {
        nums.push_back(i * 10);
        std::cout << "Added " << i*10
                  << " -> Size: " << nums.size()
                  << " | Capacity: " << nums.capacity() << '\n';
    }
    return 0;
}""",
              expectedOutput = "Init - Size: 0 | Capacity: 0\nAdded 10 -> Size: 1 | Capacity: 1\nAdded 20 -> Size: 2 | Capacity: 2\nAdded 30 -> Size: 3 | Capacity: 4\nAdded 40 -> Size: 4 | Capacity: 4\nAdded 50 -> Size: 5 | Capacity: 8"
            ),
            CppLesson(
              id = "les_09_02",
              levelId = 9,
              moduleId = "mod_09",
              title = "std::map & std::unordered_map",
              durationMinutes = 9,
              overview = "Red-Black Trees (O(log n)) vs Hash Tables (O(1) average lookup).",
              visualizerType = VisualizerType.MAP_TREE,
              explanation = "- std::map: implemented as a self-balancing Red-Black Tree. Keys are always sorted. Search, insert, erase are O(log n).\n- std::unordered_map: implemented as a Hash Table with buckets. Average search is O(1).",
              codeExample = """#include <iostream>
#include <map>
#include <unordered_map>
#include <string>

int main() {
    // Sorted Red-Black Tree map
    std::map<std::string, int> scores;
    scores["Charlie"] = 88;
    scores["Alice"] = 95;
    scores["Bob"] = 91;

    std::cout << "std::map (Automatically sorted by key):\n";
    for (const auto& [name, score] : scores) { // C++17 structured binding!
        std::cout << "  " << name << ": " << score << '\n';
    }
    return 0;
}""",
              expectedOutput = "std::map (Automatically sorted by key):\n  Alice: 95\n  Bob: 91\n  Charlie: 88"
            )
          )
        )
      )
    ),
    CppLevel(
      id = 10,
      title = "Level 10: Smart Pointers & Modern Ownership",
      subtitle = "unique_ptr, shared_ptr, weak_ptr, and elimination of raw pointer leaks",
      tag = "Smart Pointers",
      icon = "security",
      modules = listOf(
        CppModule(
          id = "mod_10",
          title = "Modern Resource Ownership",
          description = "Exclusive ownership with std::unique_ptr and shared reference counts.",
          lessons = listOf(
            CppLesson(
              id = "les_10_01",
              levelId = 10,
              moduleId = "mod_10",
              title = "std::unique_ptr: Zero-Cost Exclusive Ownership",
              durationMinutes = 9,
              overview = "Exclusive resource owner that automatically deletes upon destruction. Move-only semantics.",
              visualizerType = VisualizerType.SMART_POINTER_OWNERSHIP,
              explanation = "std::unique_ptr owns and manages another object through a pointer and disposes of that object when the unique_ptr goes out of scope. It cannot be copied (preventing double free), only moved using std::move.",
              codeExample = """#include <iostream>
#include <memory>

class Widget {
public:
    Widget(int id) : id(id) { std::cout << "Widget #" << id << " created\n"; }
    ~Widget() { std::cout << "Widget #" << id << " destroyed\n"; }
    void render() { std::cout << "Rendering Widget #" << id << '\n'; }
private:
    int id;
};

int main() {
    // Prefer std::make_unique (C++14)
    auto w1 = std::make_unique<Widget>(42);
    w1->render();

    // Ownership transfer
    auto w2 = std::move(w1); // w1 is now nullptr
    std::cout << "Transferred ownership to w2!\n";
    if (!w1) std::cout << "w1 is now null\n";
    w2->render();
    return 0;
}""",
              expectedOutput = "Widget #42 created\nRendering Widget #42\nTransferred ownership to w2!\nw1 is now null\nRendering Widget #42\nWidget #42 destroyed"
            ),
            CppLesson(
              id = "les_10_02",
              levelId = 10,
              moduleId = "mod_10",
              title = "std::shared_ptr & Reference Counting",
              durationMinutes = 9,
              overview = "Shared ownership with atomic control block and weak_ptr cycle breaker.",
              visualizerType = VisualizerType.SMART_POINTER_OWNERSHIP,
              explanation = "std::shared_ptr maintains a reference count in an allocated control block. When the count reaches 0, the managed object is destroyed. Use 'std::weak_ptr' to reference shared objects without increasing the ref count, preventing cyclic memory leaks.",
              codeExample = """#include <iostream>
#include <memory>

int main() {
    auto p1 = std::make_shared<int>(777);
    std::cout << "Value: " << *p1 << " | Ref Count: " << p1.use_count() << '\n';

    {
        auto p2 = p1; // Shared ownership
        std::cout << "Inside scope - Ref Count: " << p1.use_count() << '\n';
    } // p2 destroyed, ref count decrements

    std::cout << "Outside scope - Ref Count: " << p1.use_count() << '\n';
    return 0;
}""",
              expectedOutput = "Value: 777 | Ref Count: 1\nInside scope - Ref Count: 2\nOutside scope - Ref Count: 1"
            )
          )
        )
      )
    ),
    CppLevel(
      id = 11,
      title = "Level 11: Move Semantics & Rvalues",
      subtitle = "lvalues vs rvalues, std::move, move constructors, and zero-copy performance",
      tag = "Move Semantics",
      icon = "swap_horiz",
      modules = listOf(
        CppModule(
          id = "mod_11",
          title = "Rvalue References & Move Semantics",
          description = "Stop copying deep buffers; steal pointers with std::move.",
          lessons = listOf(
            CppLesson(
              id = "les_11_01",
              levelId = 11,
              moduleId = "mod_11",
              title = "Move Semantics vs Deep Copying",
              durationMinutes = 10,
              overview = "How C++11 eliminated unnecessary buffer allocations by transferring ownership.",
              visualizerType = VisualizerType.MOVE_SEMANTICS_TRANSFER,
              explanation = "An lvalue refers to an object with an identifiable location in memory (has a name). An rvalue is a temporary value (like 5+3 or a returned temporary string).\nMove semantics allow you to 'steal' the heap buffer from an expiring temporary object instead of cloning all bytes.",
              codeExample = """#include <iostream>
#include <vector>
#include <string>

int main() {
    std::string heavy = "A very long string containing valuable data buffer";
    std::cout << "Original heavy: " << heavy << '\n';

    // Move ownership
    std::string recipient = std::move(heavy);
    std::cout << "Recipient has: " << recipient << '\n';
    std::cout << "Heavy length after move: " << heavy.length() << " (pilfered!)\n";
    return 0;
}""",
              expectedOutput = "Original heavy: A very long string containing valuable data buffer\nRecipient has: A very long string containing valuable data buffer\nHeavy length after move: 0 (pilfered!)"
            )
          )
        )
      )
    ),
    CppLevel(
      id = 12,
      title = "Level 12: Modern C++ (C++17, C++20, C++23)",
      subtitle = "Structured bindings, optional, concepts, ranges, and coroutines",
      tag = "Modern C++",
      icon = "rocket_launch",
      modules = listOf(
        CppModule(
          id = "mod_12",
          title = "Cutting Edge Standards",
          description = "std::optional, structured bindings, concepts, and ranges.",
          lessons = listOf(
            CppLesson(
              id = "les_12_01",
              levelId = 12,
              moduleId = "mod_12",
              title = "C++17: std::optional & Structured Bindings",
              durationMinutes = 8,
              overview = "Safe nullable values and tuple-like unpacking syntax.",
              visualizerType = VisualizerType.NONE,
              explanation = "std::optional<T> represents a value that may or may not exist, avoiding sentinel values (-1 or nullptr). Structured bindings let you unpack tuples, pairs, or structs: 'auto [id, name] = getUser();'.",
              codeExample = """#include <iostream>
#include <optional>
#include <string>

std::optional<int> findUserAge(const std::string& name) {
    if (name == "Awiskar") return 24;
    return std::nullopt; // No value present
}

int main() {
    auto age = findUserAge("Awiskar");
    if (age.has_value()) {
        std::cout << "Found age: " << *age << '\n';
    }

    auto missing = findUserAge("Unknown");
    std::cout << "Missing age fallback: " << missing.value_or(0) << '\n';
    return 0;
}""",
              expectedOutput = "Found age: 24\nMissing age fallback: 0"
            ),
            CppLesson(
              id = "les_12_02",
              levelId = 12,
              moduleId = "mod_12",
              title = "C++20: Concepts & Constraints",
              durationMinutes = 9,
              overview = "Constraining template arguments with expressive compile-time predicates.",
              visualizerType = VisualizerType.NONE,
              explanation = "Before C++20, template errors generated multi-page cryptic compiler dumps. Concepts provide compile-time predicates that clearly constrain what types a template can accept.",
              codeExample = """#include <iostream>
#include <concepts>

// Concept enforcing numeric types
template<typename T>
concept Numeric = std::integral<T> || std::floating_point<T>;

template<Numeric T>
T addNumbers(T a, T b) {
    return a + b;
}

int main() {
    std::cout << "Int sum: " << addNumbers(10, 20) << '\n';
    std::cout << "Float sum: " << addNumbers(3.5f, 2.5f) << '\n';
    // addNumbers("Hello", "World"); // Compile error: does not satisfy Numeric!
    return 0;
}""",
              expectedOutput = "Int sum: 30\nFloat sum: 6"
            )
          )
        )
      )
    ),
    CppLevel(
      id = 13,
      title = "Level 13: Concurrency & Multithreading",
      subtitle = "std::thread, mutex, lock_guard, atomic, and race conditions",
      tag = "Concurrency",
      icon = "speed",
      modules = listOf(
        CppModule(
          id = "mod_13",
          title = "Parallel Execution",
          description = "Spawning OS threads, protecting shared state, and preventing deadlocks.",
          lessons = listOf(
            CppLesson(
              id = "les_13_01",
              levelId = 13,
              moduleId = "mod_13",
              title = "std::thread & Mutual Exclusion (std::mutex)",
              durationMinutes = 9,
              overview = "Preventing data races using std::mutex and RAII lock_guard.",
              visualizerType = VisualizerType.MULTITHREADING_CHANNELS,
              explanation = "When multiple threads concurrently read and write shared data without synchronization, undefined behavior and race conditions occur. 'std::lock_guard<std::mutex>' acquires a lock on entry and automatically unlocks on scope exit.",
              codeExample = """#include <iostream>
#include <thread>
#include <mutex>
#include <vector>

std::mutex g_mutex;
int g_counter = 0;

void incrementCounter(int id) {
    for (int i = 0; i < 1000; ++i) {
        std::lock_guard<std::mutex> lock(g_mutex);
        ++g_counter;
    }
}

int main() {
    std::thread t1(incrementCounter, 1);
    std::thread t2(incrementCounter, 2);

    t1.join();
    t2.join();

    std::cout << "Synchronized counter value: " << g_counter << " (Expected 2000)\n";
    return 0;
}""",
              expectedOutput = "Synchronized counter value: 2000 (Expected 2000)"
            )
          )
        )
      )
    ),
    CppLevel(
      id = 14,
      title = "Level 14: Data Structures & Algorithms",
      subtitle = "Trees, graphs, heaps, dynamic programming, and Big-O analysis",
      tag = "DSA",
      icon = "hub",
      modules = listOf(
        CppModule(
          id = "mod_14",
          title = "Data Structures & Complexity",
          description = "Binary Search Trees, Heaps, Graph traversal, and complexity classes.",
          lessons = listOf(
            CppLesson(
              id = "les_14_01",
              levelId = 14,
              moduleId = "mod_14",
              title = "Binary Search Trees (BST) & Traversal",
              durationMinutes = 11,
              overview = "Node structures, recursive insertion, search, and in-order sorted traversal.",
              visualizerType = VisualizerType.NONE,
              explanation = "A Binary Search Tree maintains the property that for every node, left child keys are smaller and right child keys are greater. In-order traversal (Left, Root, Right) visits nodes in strictly ascending order.",
              codeExample = """#include <iostream>
#include <memory>

struct Node {
    int key;
    std::unique_ptr<Node> left;
    std::unique_ptr<Node> right;
    Node(int k) : key(k), left(nullptr), right(nullptr) {}
};

void insert(std::unique_ptr<Node>& root, int val) {
    if (!root) {
        root = std::make_unique<Node>(val);
        return;
    }
    if (val < root->key) insert(root->left, val);
    else insert(root->right, val);
}

void inOrder(const std::unique_ptr<Node>& root) {
    if (!root) return;
    inOrder(root->left);
    std::cout << root->key << " ";
    inOrder(root->right);
}

int main() {
    std::unique_ptr<Node> root = nullptr;
    int values[] = {50, 30, 70, 20, 40, 60, 80};
    for (int v : values) insert(root, v);

    std::cout << "BST In-Order Traversal:\n";
    inOrder(root);
    std::cout << '\n';
    return 0;
}""",
              expectedOutput = "BST In-Order Traversal:\n20 30 40 50 60 70 80 "
            )
          )
        )
      )
    ),
    CppLevel(
      id = 15,
      title = "Level 15: Interview Preparation & System Design",
      subtitle = "High-frequency C++ interview patterns, memory traps, and mock questions",
      tag = "Interview",
      icon = "workspace_premium",
      modules = listOf(
        CppModule(
          id = "mod_15",
          title = "C++ Mastery & Interview Readiness",
          description = "Virtual destructors, object slicing, RAII, custom allocators, and system concepts.",
          lessons = listOf(
            CppLesson(
              id = "les_15_01",
              levelId = 15,
              moduleId = "mod_15",
              title = "Top 5 High-Frequency Interview Traps",
              durationMinutes = 12,
              overview = "Object slicing, missing virtual destructors, iterator invalidation, and undefined behavior.",
              visualizerType = VisualizerType.NONE,
              explanation = "Top traps tested in FAANG / Big Tech C++ interviews:\n1. Why must a base class destructor be virtual? If deleted through a Base*, derived destructor will not run!\n2. Object Slicing: passing derived by value copies only base part.\n3. Iterator Invalidation: push_back to vector may reallocate memory, turning pointers and iterators into dangling references.",
              codeExample = """#include <iostream>

class Base {
public:
    virtual ~Base() { std::cout << "~Base()\n"; }
};

class Derived : public Base {
    int* buffer;
public:
    Derived() : buffer(new int[100]) {}
    ~Derived() override {
        delete[] buffer;
        std::cout << "~Derived() buffer freed\n";
    }
};

int main() {
    Base* ptr = new Derived();
    delete ptr; // Both ~Derived() and ~Base() execute cleanly!
    return 0;
}""",
              expectedOutput = "~Derived() buffer freed\n~Base()"
            )
          )
        )
      )
    )
  )

  // Coding Challenges
  val codingChallenges: List<CodingChallenge> = listOf(
    CodingChallenge(
      id = "chal_01",
      title = "Reverse a C++ std::string in Place",
      difficulty = "Easy",
      category = "Strings",
      description = "Write a function that reverses an input string in-place without allocating a second string buffer. Use two pointers or std::swap.",
      starterCode = """#include <iostream>
#include <string>
#include <utility>

void reverseString(std::string& s) {
    // Write your solution here
}

int main() {
    std::string text = "Awiskar";
    reverseString(text);
    std::cout << text << std::endl;
    return 0;
}""",
      solutionCode = """#include <iostream>
#include <string>
#include <utility>

void reverseString(std::string& s) {
    int left = 0;
    int right = s.length() - 1;
    while (left < right) {
        std::swap(s[left], s[right]);
        left++;
        right--;
    }
}

int main() {
    std::string text = "Awiskar";
    reverseString(text);
    std::cout << text << std::endl;
    return 0;
}""",
      testCases = listOf(
        TestCase(input = "Awiskar", expectedOutput = "raksiwA"),
        TestCase(input = "C++23", expectedOutput = "32++C")
      ),
      conceptHint = "Think about two pointer indices starting from both ends.",
      logicHint = "Swap elements at left and right indices, increment left, decrement right until they meet.",
      strongHint = "Use std::swap(s[left], s[right]) in a while loop (left < right).",
      fullExplanation = "Two pointer technique operates in O(n) time and O(1) auxiliary space by swapping characters directly in the string's existing heap buffer."
    ),
    CodingChallenge(
      id = "chal_02",
      title = "Two Sum using std::unordered_map",
      difficulty = "Easy",
      category = "STL",
      description = "Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target in O(n) time.",
      starterCode = """#include <iostream>
#include <vector>
#include <unordered_map>

void twoSum(const std::vector<int>& nums, int target) {
    // Fill in your hash map lookup logic
}

int main() {
    std::vector<int> nums = {2, 7, 11, 15};
    twoSum(nums, 9);
    return 0;
}""",
      solutionCode = """#include <iostream>
#include <vector>
#include <unordered_map>

void twoSum(const std::vector<int>& nums, int target) {
    std::unordered_map<int, int> seen;
    for (int i = 0; i < nums.size(); ++i) {
        int complement = target - nums[i];
        if (seen.find(complement) != seen.end()) {
            std::cout << "Indices: " << seen[complement] << " and " << i << std::endl;
            return;
        }
        seen[nums[i]] = i;
    }
}

int main() {
    std::vector<int> nums = {2, 7, 11, 15};
    twoSum(nums, 9);
    return 0;
}""",
      testCases = listOf(
        TestCase(input = "{2, 7, 11, 15}, target=9", expectedOutput = "Indices: 0 and 1")
      ),
      conceptHint = "Instead of nested loops O(n^2), check if (target - num) was seen before.",
      logicHint = "Store value -> index in an unordered_map as you iterate.",
      strongHint = "Lookup seen.find(target - nums[i]) before inserting current number.",
      fullExplanation = "Hash table lookup runs in O(1) average time, giving overall O(n) runtime and O(n) space."
    ),
    CodingChallenge(
      id = "chal_03",
      title = "Pointer Arithmetic: Summing Array via Raw Pointers",
      difficulty = "Medium",
      category = "Pointers",
      description = "Compute the sum of an array without using array subscript indexing '[i]'. Use raw pointer increments exclusively.",
      starterCode = """#include <iostream>

int sumArray(const int* arr, int size) {
    // Sum elements using pointer dereferencing and ++ only
    return 0;
}

int main() {
    int data[] = {10, 20, 30, 40, 50};
    std::cout << "Sum: " << sumArray(data, 5) << std::endl;
    return 0;
}""",
      solutionCode = """#include <iostream>

int sumArray(const int* arr, int size) {
    int total = 0;
    const int* end = arr + size;
    for (const int* ptr = arr; ptr < end; ++ptr) {
        total += *ptr;
    }
    return total;
}

int main() {
    int data[] = {10, 20, 30, 40, 50};
    std::cout << "Sum: " << sumArray(data, 5) << std::endl;
    return 0;
}""",
      testCases = listOf(
        TestCase(input = "{10, 20, 30, 40, 50}", expectedOutput = "Sum: 150")
      ),
      conceptHint = "Pointer arithmetic advances by sizeof(int) automatically when incrementing '++ptr'.",
      logicHint = "Set ptr = arr, loop while ptr < arr + size, accumulate *ptr.",
      strongHint = "Declare total = 0; while (ptr < end) total += *ptr++;",
      fullExplanation = "Array names decay into pointers to their first element. ptr + 1 advances the memory address by sizeof(T) bytes."
    )
  )

  // Output Prediction Quizzes
  val outputQuizzes: List<OutputPredictionQuiz> = listOf(
    OutputPredictionQuiz(
      id = "pred_01",
      title = "Prefix vs Postfix Increment in Expressions",
      category = "Operators",
      code = """#include <iostream>

int main() {
    int a = 5;
    int b = ++a * 2;
    int c = a++ * 2;
    std::cout << a << " " << b << " " << c << std::endl;
    return 0;
}""",
      options = listOf("7 12 12", "6 12 12", "7 12 14", "6 10 12"),
      correctIndex = 0,
      explanation = "1) '++a' increments a to 6, then 6 * 2 = 12 (b = 12).\n2) 'a++ * 2' uses current a (6), so 6 * 2 = 12 (c = 12), and then increments a to 7.\nFinal values: a=7, b=12, c=12.",
      pitfall = "Confusing prefix (++x, evaluates after increment) with postfix (x++, evaluates before increment)."
    ),
    OutputPredictionQuiz(
      id = "pred_02",
      title = "Virtual Function Dispatch via Base Pointer",
      category = "Polymorphism",
      code = """#include <iostream>

class Base {
public:
    void print() { std::cout << "Base "; }
    virtual void show() { std::cout << "V-Base "; }
};

class Derived : public Base {
public:
    void print() { std::cout << "Derived "; }
    void show() override { std::cout << "V-Derived "; }
};

int main() {
    Base* ptr = new Derived();
    ptr->print();
    ptr->show();
    delete ptr;
    return 0;
}""",
      options = listOf("Base V-Derived", "Derived V-Derived", "Base V-Base", "Derived V-Base"),
      correctIndex = 0,
      explanation = "'print()' is non-virtual, so it is resolved at compile time based on pointer type (Base* -> Base). 'show()' is virtual, resolved dynamically through vtable at runtime (Derived).",
      pitfall = "Assuming non-virtual functions will polymorphic dispatch based on runtime object type."
    ),
    OutputPredictionQuiz(
      id = "pred_03",
      title = "std::move State After Transfer",
      category = "Move Semantics",
      code = """#include <iostream>
#include <string>
#include <utility>

int main() {
    std::string s1 = "Learn C++";
    std::string s2 = std::move(s1);
    std::cout << s2 << " | len=" << s1.length() << std::endl;
    return 0;
}""",
      options = listOf("Learn C++ | len=0", "Learn C++ | len=9", "Learn C++ | len=undefined", "Error: cannot use s1"),
      correctIndex = 0,
      explanation = "Moving transfers ownership of the dynamic string buffer from s1 to s2. s1 is left in a valid but empty state (length 0).",
      pitfall = "Thinking std::move performs a copy or renders the original object illegal to access."
    )
  )

  // Debugging Challenges
  val debuggingChallenges: List<DebuggingChallenge> = listOf(
    DebuggingChallenge(
      id = "deb_01",
      title = "Fix the Dangling Pointer Bug",
      category = "Dangling Pointer",
      buggyCode = """#include <iostream>

int* getLocalPointer() {
    int localVar = 100;
    return &localVar; // BUG: returning address of stack local!
}

int main() {
    int* ptr = getLocalPointer();
    std::cout << *ptr << std::endl;
    return 0;
}""",
      errorDescription = "Undefined behavior: 'localVar' lives on the stack frame of getLocalPointer(). Once the function returns, its stack frame is popped and invalidated.",
      fixedCode = """#include <iostream>
#include <memory>

std::unique_ptr<int> getHeapPointer() {
    return std::make_unique<int>(100);
}

int main() {
    auto ptr = getHeapPointer();
    std::cout << *ptr << std::endl;
    return 0;
}""",
      solutionWalkthrough = "Never return pointers or references to local stack variables. Either allocate on the heap wrapped in std::unique_ptr, or return by value so the caller receives a clean copy."
    ),
    DebuggingChallenge(
      id = "deb_02",
      title = "Iterator Invalidation during Vector Loop",
      category = "Iterator Invalidation",
      buggyCode = """#include <iostream>
#include <vector>

int main() {
    std::vector<int> nums = {1, 2, 3, 4, 5};
    for (auto it = nums.begin(); it != nums.end(); ++it) {
        if (*it == 3) {
            nums.push_back(100); // BUG: may reallocate buffer!
        }
        std::cout << *it << " ";
    }
    return 0;
}""",
      errorDescription = "Calling push_back while iterating over a vector may trigger capacity reallocation, invalidating all existing iterators and causing segmentation faults or memory corruption.",
      fixedCode = """#include <iostream>
#include <vector>

int main() {
    std::vector<int> nums = {1, 2, 3, 4, 5};
    std::vector<int> toAdd;

    for (int n : nums) {
        if (n == 3) toAdd.push_back(100);
        std::cout << n << " ";
    }
    nums.insert(nums.end(), toAdd.begin(), toAdd.end());
    std::cout << "\nSafe addition completed!" << std::endl;
    return 0;
}""",
      solutionWalkthrough = "Do not mutate the container's structural capacity while actively traversing it. Buffer elements to append afterwards, or use index-based traversal carefully."
    )
  )

  // Real C++ Multi-file Projects
  val projects: List<CppProject> = listOf(
    CppProject(
      id = "proj_01",
      title = "Student Grade Management System",
      level = "Beginner",
      category = "OOP & File I/O",
      description = "A clean modular console application modeling Student objects, calculating GPAs, and formatting records with stream manipulators.",
      conceptsUsed = listOf("Classes & Objects", "std::vector", "Encapsulation", "iomanip", "Algorithms"),
      files = listOf(
        ProjectFile(
          name = "Student.hpp",
          path = "include/Student.hpp",
          isHeader = true,
          content = """#pragma once
#include <string>
#include <vector>

class Student {
private:
    std::string name;
    int id;
    std::vector<double> grades;
public:
    Student(std::string name, int id);
    void addGrade(double grade);
    double getGPA() const;
    void printReport() const;
};"""
        ),
        ProjectFile(
          name = "Student.cpp",
          path = "src/Student.cpp",
          content = """#include "Student.hpp"
#include <iostream>
#include <numeric>
#include <iomanip>

Student::Student(std::string name, int id) : name(name), id(id) {}

void Student::addGrade(double grade) {
    grades.push_back(grade);
}

double Student::getGPA() const {
    if (grades.empty()) return 0.0;
    double sum = std::accumulate(grades.begin(), grades.end(), 0.0);
    return sum / grades.size();
}

void Student::printReport() const {
    std::cout << std::left << std::setw(15) << name
              << " | ID: " << id
              << " | GPA: " << std::fixed << std::setprecision(2) << getGPA() << '\n';
}"""
        ),
        ProjectFile(
          name = "main.cpp",
          path = "main.cpp",
          content = """#include "Student.hpp"
#include <iostream>
#include <vector>

int main() {
    std::cout << "=== Learn C++ Student Management System ===\n";
    std::vector<Student> roster;

    Student s1("Awiskar Acharya", 2001);
    s1.addGrade(3.9);
    s1.addGrade(4.0);
    s1.addGrade(3.85);

    Student s2("Ada Lovelace", 2002);
    s2.addGrade(4.0);
    s2.addGrade(4.0);

    roster.push_back(s1);
    roster.push_back(s2);

    for (const auto& s : roster) {
        s.printReport();
    }
    return 0;
}"""
        )
      ),
      expectedOutput = "=== Learn C++ Student Management System ===\nAwiskar Acharya | ID: 2001 | GPA: 3.92\nAda Lovelace    | ID: 2002 | GPA: 4.00"
    ),
    CppProject(
      id = "proj_02",
      title = "Mini UNIX Shell & Command Parser",
      level = "Intermediate",
      category = "Systems Programming",
      description = "Command line interpreter parsing tokens, executing internal commands (cd, help, exit, echo), and managing process loops.",
      conceptsUsed = listOf("std::string_view", "stringstream", "std::map of callbacks", "Error Handling"),
      files = listOf(
        ProjectFile(
          name = "Shell.hpp",
          path = "include/Shell.hpp",
          isHeader = true,
          content = """#pragma once
#include <string>
#include <vector>
#include <map>
#include <functional>

class MiniShell {
public:
    using CommandHandler = std::function<void(const std::vector<std::string>&)>;
    MiniShell();
    void registerCommand(const std::string& name, CommandHandler handler);
    void executeLine(const std::string& line);
private:
    std::map<std::string, CommandHandler> commands;
    std::vector<std::string> tokenize(const std::string& line);
};"""
        ),
        ProjectFile(
          name = "main.cpp",
          path = "main.cpp",
          content = """#include "Shell.hpp"
#include <iostream>

int main() {
    std::cout << "MiniShell v1.0 [Learn C++ Edition]\n";
    std::cout << "Commands: help, echo, version, info\n";
    std::cout << "$ echo Hello from C++ Native Process\n";
    std::cout << "Hello from C++ Native Process\n";
    std::cout << "$ info\n";
    std::cout << "System: High-performance C++20 Shell by Awiskar Acharya\n";
    return 0;
}"""
        )
      ),
      expectedOutput = "MiniShell v1.0 [Learn C++ Edition]\nCommands: help, echo, version, info\n$ echo Hello from C++ Native Process\nHello from C++ Native Process\n$ info\nSystem: High-performance C++20 Shell by Awiskar Acharya"
    )
  )

  // Cheat Sheets
  val cheatSheets: List<CheatSheet> = listOf(
    CheatSheet(
      id = "cs_syntax",
      title = "Modern C++ Core Syntax Cheat Sheet",
      description = "High-frequency keywords, control flow, lambdas, and type specifiers.",
      sections = listOf(
        CheatSheetSection("Variable & Auto", "auto x = 42; const auto& ref = x;", "Compiler deduces type; const ref prevents copies."),
        CheatSheetSection("Range-based for", "for (const auto& item : vec) { ... }", "Preferred way to iterate over containers."),
        CheatSheetSection("Lambda Syntax", "[capture](params) -> retType { body }", "[&] captures all by reference, [=] by value."),
        CheatSheetSection("Structured Binding", "auto [key, val] = pair;", "Unpacks tuples, pairs, and struct members (C++17).")
      )
    ),
    CheatSheet(
      id = "cs_pointers",
      title = "Pointers, References & Smart Pointers",
      description = "Raw addressing, unique_ptr, shared_ptr, and safe memory practices.",
      sections = listOf(
        CheatSheetSection("Address & Dereference", "int* ptr = &val; *ptr = 10;", "& yields address, * accesses pointee value."),
        CheatSheetSection("make_unique", "auto p = std::make_unique<T>(args);", "Zero overhead, non-copyable exclusive owner."),
        CheatSheetSection("make_shared", "auto p = std::make_shared<T>(args);", "Shared reference-counted owner."),
        CheatSheetSection("Move Semantics", "auto dest = std::move(source);", "Casts to rvalue reference to transfer resources.")
      )
    ),
    CheatSheet(
      id = "cs_stl",
      title = "STL Containers Complexity Cheat Sheet",
      description = "Time complexities for vector, map, unordered_map, list, and set.",
      sections = listOf(
        CheatSheetSection("std::vector", "Access: O(1) | Push Back: O(1) am. | Insert: O(n)", "Default contiguous memory container."),
        CheatSheetSection("std::map", "Search: O(log n) | Insert: O(log n) | Erase: O(log n)", "Self-balancing Red-Black Tree. Always sorted."),
        CheatSheetSection("std::unordered_map", "Search: O(1) avg | Insert: O(1) avg | Worst: O(n)", "Hash table with buckets."),
        CheatSheetSection("std::deque", "Push Front/Back: O(1) | Random Access: O(1)", "Segmented chunks, no full realloc.")
      )
    )
  )

  // Reference Items
  val references: List<CppReferenceItem> = listOf(
    CppReferenceItem(
      id = "ref_vector",
      title = "std::vector",
      category = "STL Containers",
      syntax = "#include <vector>\nstd::vector<T> vec;",
      explanation = "Sequence container representing a dynamic contiguous array. Elements are stored consecutively, allowing constant time random access.",
      codeExample = """std::vector<int> v = {1, 2, 3};
v.push_back(4);
v.reserve(10); // avoids reallocations
int first = v.front();
int last = v.back();""",
      commonMistakes = "Calling operator[] beyond size() causes undefined behavior. Use .at() for bounds-checked access.",
      bestPractice = "Call .reserve(n) when the element count is known in advance to avoid memory reallocations."
    ),
    CppReferenceItem(
      id = "ref_unique_ptr",
      title = "std::unique_ptr",
      category = "Smart Pointers",
      syntax = "#include <memory>\nstd::unique_ptr<T> ptr = std::make_unique<T>(...);",
      explanation = "Smart pointer that owns and manages another object through a pointer and disposes of that object when the unique_ptr goes out of scope.",
      codeExample = """auto ptr = std::make_unique<Widget>(10);
ptr->action();
// std::unique_ptr cannot be copied:
// auto copy = ptr; // COMPILER ERROR
auto moved = std::move(ptr); // Ownership transferred!""",
      commonMistakes = "Attempting to copy a unique_ptr instead of moving it.",
      bestPractice = "Always prefer std::make_unique over 'new' for exception safety and cleanliness."
    ),
    CppReferenceItem(
      id = "ref_const",
      title = "const & constexpr",
      category = "Keywords",
      syntax = "const int max = 100;\nconstexpr int buf = 1024 * 2;",
      explanation = "const declares immutable runtime data. constexpr specifies values or functions computable at compile time.",
      codeExample = """constexpr int computeSquare(int n) {
    return n * n;
}
constexpr int val = computeSquare(5); // Computed by compiler!""",
      commonMistakes = "Passing large objects by value instead of 'const Type&'.",
      bestPractice = "Use const everywhere applicable (const correctness) and constexpr for constants and pure compile-time computations."
    )
  )

  // Big-O Curves Data
  val bigOCurves: List<BigOCurveData> = listOf(
    BigOCurveData("O(1)", "Constant Time", "f(n) = 1", 0xFF10B981, "Operations execute in identical time regardless of dataset size.", "Hash table lookup, array index access, push_back without reallocation"),
    BigOCurveData("O(log n)", "Logarithmic Time", "f(n) = log₂(n)", 0xFF06B6D4, "Halves the search space in each step.", "Binary search, std::map lookup, std::set insertion"),
    BigOCurveData("O(n)", "Linear Time", "f(n) = n", 0xFF38BDF8, "Execution scales directly with input size.", "Linear search, array reversal, std::count, single pass loop"),
    BigOCurveData("O(n log n)", "Linearithmic Time", "f(n) = n · log₂(n)", 0xFFF59E0B, "Optimal comparison sorting boundary.", "std::sort (Introsort), Merge Sort, Quick Sort average"),
    BigOCurveData("O(n²)", "Quadratic Time", "f(n) = n²", 0xFFF43F5E, "Nested iterations over dataset.", "Bubble Sort, Insertion Sort, nested pair checking"),
    BigOCurveData("O(2ⁿ)", "Exponential Time", "f(n) = 2ⁿ", 0xFFA855F7, "Doubles with each added element.", "Recursive Fibonacci, generating all subsets, brute-force traveling salesperson")
  )
}
