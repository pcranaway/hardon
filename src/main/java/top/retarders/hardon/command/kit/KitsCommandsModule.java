package top.retarders.hardon.command.kit;

import me.lucko.helper.Commands;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import top.retarders.hardon.command.kit.handler.*;

public class KitsCommandsModule implements TerminableModule {
    @Override
    public void setup(TerminableConsumer consumer) {
        Commands.create()
                .assertPlayer()
                .assertOp()
                .description("Opens kit management GUI")
                .handler(new KitCommandHandler())
                .registerAndBind(consumer, "kit", "kits");

        Commands.create()
                .assertPlayer()
                .assertOp()
                .assertUsage("<name>")
                .description("Creates a new kit")
                .handler(new NewKitCommandHandler())
                .registerAndBind(consumer, "newkit");

        Commands.create()
                .assertPlayer()
                .assertOp()
                .assertUsage("<name>")
                .description("Sets the inventory of a kit")
                .handler(new SetKitCommandHandler())
                .registerAndBind(consumer, "setkit");

        Commands.create()
                .assertPlayer()
                .assertOp()
                .assertUsage("<name>")
                .description("Sets the display item of a kit")
                .handler(new SetDisplayCommandHandler())
                .registerAndBind(consumer, "setdisplay");

        Commands.create()
                .assertPlayer()
                .assertOp()
                .assertUsage("<name> <description>")
                .description("Sets the description of a kit")
                .handler(new SetDescriptionCommandHandler())
                .registerAndBind(consumer, "setdescription", "setdesc");

        Commands.create()
                .assertPlayer()
                .assertOp()
                .assertUsage("<name> <price>")
                .description("Sets the price of a kit")
                .handler(new SetPriceCommandHandler())
                .registerAndBind(consumer, "setprice");

        Commands.create()
                .assertPlayer()
                .assertOp()
                .assertUsage("<name>")
                .description("Deletes a kit")
                .handler(new DeleteKitCommandHandler())
                .registerAndBind(consumer, "deletekit", "delkit");
    }
}
