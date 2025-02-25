package elocindev.eldritch_end.item.infusion_templates;

import java.util.List;

import elocindev.eldritch_end.api.infusion.InfusionTemplate;
import elocindev.eldritch_end.config.Configs;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SmithingTemplateItem;
import net.minecraft.item.SwordItem;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class EtyrTemplate extends SmithingTemplateItem implements InfusionTemplate {
    public EtyrTemplate(Text appliesToText, Text ingredientsText, Text titleText, Text baseSlotDescriptionText, Text additionsSlotDescriptionText, List<Identifier> emptyBaseSlotTextures, List<Identifier> emptyAdditionsSlotTextures) {
        super(appliesToText, ingredientsText, titleText, baseSlotDescriptionText, additionsSlotDescriptionText, emptyBaseSlotTextures, emptyAdditionsSlotTextures);
    }

    @Override
    public boolean isEquipmentAllowed(ItemStack stack) {
        if (stack.getItem() instanceof ArmorItem && Configs.Mechanics.INFUSIONS.etyr_infusion.can_apply_to_armor
            || (stack.getItem() instanceof SwordItem || stack.getItem() instanceof AxeItem) && Configs.Mechanics.INFUSIONS.etyr_infusion.can_apply_to_weapons) {
            return true;
        }

        return false;
    }
}