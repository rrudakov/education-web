(ns education.utils.editor
  (:require ["draft-js" :refer [convertFromRaw convertToRaw EditorState]]))

(defn raw-editor-state
  "Convert `editor-state` to raw to save to database."
  [editor-state]
  (.stringify js/JSON (convertToRaw (.getCurrentContent editor-state))))

(defn to-editor-state
  "Convert raw state to `EditorState` object."
  [raw]
  (.createWithContent EditorState (convertFromRaw (.parse js/JSON raw))))
