package de.my.playground.misc;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by jensfoerster on 9/26/2015.
 */
public class ExpandableListItem<T> {

    private T value;
    private Type type;
    private SubType subType;
    public boolean isCollapsed;
    public List<ExpandableListItem> children;

    public ExpandableListItem(T value, Type type) {
        Initialize(value, type, SubType.NONE);
    }

    public ExpandableListItem(T value, Type type, SubType subType) {
        Initialize(value, type, subType);
    }

    private void Initialize(T value, Type type, SubType subType){
        this.value = value;
        this.type = type;
        this.subType = subType;
        children = new ArrayList<>();
        isCollapsed = false;
    }

    public T getValue() {
        return value;
    }

    public Type getType() {
        return type;
    }

    public SubType getSubType() {
        return subType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ExpandableListItem: [");
        if(type!=null){
            sb.append("type: [").append(type.toString()).append("] ");
        }
        if(value!=null){
            sb.append("value: [").append(value.toString()).append("]");
        }
        if(!children.isEmpty()){
            sb.append(" children: [").append(children.size()).append("] ");
            sb.append("isCollapsed: [").append(isCollapsed).append("]");
        }
        sb.append("]");

        return sb.toString();
    }

    public enum Type {
        HEADING(1),
        CHILD(2),
        MISC(3),
        UNKNOWN(99);

        private int value;

        Type(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static Type fromInt(int i) {
            for (Type t : Type.values()) {
                if (t.getValue() == i) {
                    return t;
                }
            }
            return UNKNOWN;
        }
    }
    public enum SubType{
        NONE(0),
        FRUITS(1),
        ABOUT(2);

        private int value;

        SubType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}