package com.example.data.model

data class MemoryCell(
  val address: String,
  val variableName: String,
  val typeName: String,
  val value: String,
  val pointsToAddress: String? = null,
  val isHeap: Boolean = false,
  val isFreed: Boolean = false
)

data class SmartPointerState(
  val pointerName: String,
  val type: String, // "unique_ptr" or "shared_ptr"
  val targetObjectId: String,
  val refCount: Int = 1
)

data class HeapObject(
  val id: String,
  val typeName: String,
  val payload: String,
  val address: String,
  val owners: List<String>
)

data class VectorBufferState(
  val elements: List<Int>,
  val capacity: Int,
  val lastOperation: String = ""
)

data class MapNodeState(
  val key: String,
  val value: String,
  val hashOrColor: String
)

data class ClassMember(
  val name: String,
  val type: String,
  val access: String, // "public", "private", "protected"
  val isMethod: Boolean = false,
  val params: String = ""
)

data class CustomClassDef(
  val className: String,
  val parentClass: String? = null,
  val members: List<ClassMember>
)

data class BigOCurveData(
  val notation: String,
  val name: String,
  val formula: String,
  val colorHex: Long,
  val description: String,
  val exampleAlgorithms: String
)
